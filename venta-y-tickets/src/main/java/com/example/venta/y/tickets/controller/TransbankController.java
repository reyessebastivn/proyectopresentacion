package com.example.venta.y.tickets.controller;

import cl.transbank.common.IntegrationType;
import cl.transbank.webpay.common.WebpayOptions;
import cl.transbank.webpay.exception.TransactionCommitException;
import cl.transbank.webpay.exception.TransactionCreateException;
import cl.transbank.webpay.webpayplus.WebpayPlus;
import cl.transbank.webpay.webpayplus.responses.WebpayPlusTransactionCommitResponse;
import cl.transbank.webpay.webpayplus.responses.WebpayPlusTransactionCreateResponse;
import com.example.venta.y.tickets.model.Cupon;
import com.example.venta.y.tickets.model.Pago;
import com.example.venta.y.tickets.model.response.ConfirmarResponse;
import com.example.venta.y.tickets.model.response.InicioPagoResponse;
import com.example.venta.y.tickets.service.CuponService;
import com.example.venta.y.tickets.service.PagoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/transbank")
@Tag(name = "Transbank", description = "API de transacciones")
public class TransbankController {

    private final WebClient webClient;
    private final PagoService pagoService;
    private final CuponService cuponService;
    private final WebpayPlus.Transaction transaction;

    private final Map<String, Long> userMap = new HashMap<>();
    private final Map<String, Long> perfumeMap = new HashMap<>();
    private final Map<String, Integer> precioMap = new HashMap<>();
    private final Map<String, String> cuponMap = new HashMap<>();

    public TransbankController(@Qualifier("webClientUsuarios") WebClient webClient,
                               PagoService pagoService,
                               CuponService cuponService) {
        this.webClient = webClient;
        this.pagoService = pagoService;
        this.cuponService = cuponService;

        WebpayOptions options = new WebpayOptions(
            "597055555532",
            "579B532A7440BB0C9079DED94D31EA1615BACEB56610332264630D42D0A36B1C",
            IntegrationType.TEST
        );
        this.transaction = new WebpayPlus.Transaction(options);
    }

    @PostMapping("/crear")
    @Operation(summary = "Crear transacción de pago",
               description = "Inicia una transacción en Transbank con la validación de usuario, perfume y aplicación de cupones de descuento.")
    public ResponseEntity<InicioPagoResponse> iniciarPago(@RequestBody Pago request) {
        InicioPagoResponse response = new InicioPagoResponse();

        try {
            // Validar usuario
            var usuario = webClient.get()
                .uri("http://localhost:8081/usuarios/" + request.getIdUsuario())
                .retrieve()
                .bodyToMono(Object.class)
                .block();

            if (usuario == null) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Usuario no existe");
            }

            // Validar perfume
            var perfume = webClient.get()
                .uri("http://localhost:8082/perfumes/" + request.getIdPerfume())
                .retrieve()
                .bodyToMono(Object.class)
                .block();

            if (perfume == null) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Perfume no existe");
            }

            // Validar cantidad
            if (request.getCantidad() == null || request.getCantidad() <= 0) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Cantidad inválida");
            }

            // Calcular precio final con regla de cupones
            double precioFinal;
            String codigoCupon = request.getCodigoCupon();

            if ("DESC30".equalsIgnoreCase(codigoCupon) && request.getCantidad() >= 15) {
                precioFinal = request.getPrecio() * request.getCantidad() * 0.7;

            } else if ("DESC20".equalsIgnoreCase(codigoCupon) && request.getCantidad() >= 7) {
                precioFinal = request.getPrecio() * request.getCantidad() * 0.8;

            } else if ("DSC10".equalsIgnoreCase(codigoCupon) && request.getCantidad() >= 3) {
                precioFinal = request.getPrecio() * request.getCantidad() * 0.9;

            } else {
                precioFinal = request.getPrecio() * request.getCantidad();
            }

            int precio = (int) Math.round(precioFinal);

            String buyOrder = "orden-" + UUID.randomUUID();
            String sessionId = UUID.randomUUID().toString();
            String returnUrl = "http://localhost:8080/webpay/confirmar";

            // Guardar en mapas temporales
            userMap.put(sessionId, request.getIdUsuario());
            perfumeMap.put(sessionId, request.getIdPerfume());
            precioMap.put(sessionId, precio);
            cuponMap.put(sessionId, codigoCupon);

            WebpayPlusTransactionCreateResponse createResponse = transaction.create(
                buyOrder,
                sessionId,
                precio,
                returnUrl
            );

            response.setExito(true);
            response.setUrlPago(createResponse.getUrl() + "?token_ws=" + createResponse.getToken());
            response.setMensaje("Transacción registrada con éxito");
            return ResponseEntity.ok(response);

        } catch (TransactionCreateException e) {
            response.setExito(false);
            response.setMensaje("Error al generar token de Transbank: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        } catch (ResponseStatusException e) {
            response.setExito(false);
            response.setMensaje(e.getReason());
            return ResponseEntity.status(e.getStatusCode()).body(response);
        } catch (Exception e) {
            response.setExito(false);
            response.setMensaje("Error inesperado: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @GetMapping("/confirmar")
    @Operation(summary = "Confirmar transacción de pago",
               description = "Confirma el pago realizado en Transbank, guarda la información de la transacción y devuelve el resultado final al usuario.")
    public ResponseEntity<ConfirmarResponse> confirmarPago(
            @Parameter(description = "Token de confirmación devuelto por Transbank", required = true)
            @RequestParam("token_ws") String tokenWs) {
        ConfirmarResponse response = new ConfirmarResponse();
        try {
            WebpayPlusTransactionCommitResponse commitResponse = transaction.commit(tokenWs);

            String sessionId = commitResponse.getSessionId();
            Long userId = userMap.get(sessionId);
            Long perfumeId = perfumeMap.get(sessionId);
            Integer precio = precioMap.get(sessionId);
            String codigoCupon = cuponMap.get(sessionId);

            pagoService.guardarPago(userId, perfumeId, precio, LocalDate.now(), codigoCupon, commitResponse.getBuyOrder());

            // Limpiar mapas
            userMap.remove(sessionId);
            perfumeMap.remove(sessionId);
            precioMap.remove(sessionId);
            cuponMap.remove(sessionId);

            response.setExito(true);
            response.setMensaje("¡Listo! Hemos recibido tu pago. Transacción: " + commitResponse.getBuyOrder());
            return ResponseEntity.ok(response);

        } catch (TransactionCommitException e) {
            response.setExito(false);
            response.setMensaje("No se pudo confirmar tu pago. Intenta nuevamente: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        } catch (Exception e) {
            response.setExito(false);
            response.setMensaje("Se produjo un error durante la validación: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
}
