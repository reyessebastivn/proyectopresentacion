package com.example.venta.y.tickets.controller;





import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ResponseStatusException;

import com.example.venta.y.tickets.model.Cupon;
import com.example.venta.y.tickets.model.Pago;
import com.example.venta.y.tickets.model.response.ConfirmarResponse;
import com.example.venta.y.tickets.model.response.InicioPagoResponse;
import com.example.venta.y.tickets.service.CuponService;
import com.example.venta.y.tickets.service.PagoService;

import cl.transbank.common.IntegrationType;
import cl.transbank.webpay.common.WebpayOptions;
import cl.transbank.webpay.exception.TransactionCommitException;
import cl.transbank.webpay.exception.TransactionCreateException;
import cl.transbank.webpay.webpayplus.WebpayPlus;
import cl.transbank.webpay.webpayplus.responses.WebpayPlusMallTransactionCommitResponse;
import cl.transbank.webpay.webpayplus.responses.WebpayPlusTransactionCommitResponse;
import cl.transbank.webpay.webpayplus.responses.WebpayPlusTransactionCreateResponse;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("transbank")
public class TransbankController {

    @Autowired
    private WebClient webClient;

    @Autowired
    private PagoService pagoService;

    @Autowired
    private CuponService cuponService;

    private final WebpayPlus.Transaction transaction;

    public TransbankController() {
        WebpayOptions options = new WebpayOptions(
            "597055555532", // Comercio TEST
            "579B532A7440BB0C9079DED94D31EA1615BACEB56610332264630D42D0A36B1C",
            IntegrationType.TEST
        );
        this.transaction = new WebpayPlus.Transaction(options);
    }

    private final Map<String, Long> userMap = new HashMap<>();
    private final Map<String, Long> perfumeMap = new HashMap<>();
    private final Map<String, Integer> precioMap = new HashMap<>();
    private final Map<String, String> cuponMap = new HashMap<>();

    @PostMapping("/crear")
    public InicioPagoResponse iniciarPago(@RequestBody Pago request) {
        InicioPagoResponse response = new InicioPagoResponse();
        try {
            // Validar usuario (ajusta URL real de tu microservicio usuario)
            var usuario = webClient.get()
                .uri("http://localhost:8081/usuarios/" + request.getIdUsuario())
                .retrieve()
                .bodyToMono(Object.class)
                .block();

            if (usuario == null) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Usuario no existe");
            }

            // Validar perfume (ajusta URL real de tu microservicio perfume)
            var perfume = webClient.get()
                .uri("http://localhost:8082/perfumes/" + request.getIdPerfume())
                .retrieve()
                .bodyToMono(Object.class)
                .block();

            if (perfume == null) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Perfume no existe");
            }

            // Precio base del perfume (debes ajustarlo según tu entidad)
            double precioFinal = request.getPrecio();
            String codigoCupon = request.getCodigoCupon();

            if (codigoCupon != null && !codigoCupon.isEmpty()) {
                try {
                    Cupon cupon = cuponService.validarCupon(codigoCupon);
                    precioFinal *= (1 - cupon.getDescuento() / 100.0);
                } catch (ResponseStatusException e) {
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Falló la validación del cupón: inválido o fuera de vigenci");
                }
            }

            int precio = (int) Math.round(precioFinal);

            String buyOrder = "orden-" + UUID.randomUUID();
            String sessionId = UUID.randomUUID().toString();
            String returnUrl = "http://localhost:8080/webpay/confirmar";

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
            response.setMensaje("Transaccion registrada con exito");
        } catch (TransactionCreateException e) {
            response.setExito(false);
            response.setMensaje("- Error al generar el token de transacción con Transbank: " + e.getMessage());
        } catch (Exception e) {
            response.setExito(false);
            response.setMensaje("Error al generar la solicitud de pago: " + e.getMessage());
        }
        return response;
    }

    @GetMapping("/confirmar")
    public ConfirmarResponse confirmarPago(@RequestParam("token_ws") String tokenWs) {
        ConfirmarResponse response = new ConfirmarResponse();
        try {
            WebpayPlusTransactionCommitResponse commitResponse = transaction.commit(tokenWs);

            String sessionId = commitResponse.getSessionId();
            Long userId = userMap.get(sessionId);
            Long perfumeId = perfumeMap.get(sessionId);
            Integer precio = precioMap.get(sessionId);
            String codigoCupon = cuponMap.get(sessionId);

            pagoService.guardarPago(userId, perfumeId, precio, LocalDate.now(), codigoCupon, commitResponse.getBuyOrder());

            userMap.remove(sessionId);
            perfumeMap.remove(sessionId);
            precioMap.remove(sessionId);
            cuponMap.remove(sessionId);

            response.setExito(true);
            response.setMensaje("¡Listo! Hemos recibido tu pago.Transaccion: " + commitResponse.getBuyOrder());
        } catch (TransactionCommitException e) {
            response.setExito(false);
            response.setMensaje("No se pudo confirmar tu pago.Intenta nuevamente: " + e.getMessage());
        } catch (Exception e) {
            response.setExito(false);
            response.setMensaje("Se produjo un error durante la validación: " + e.getMessage());
        }
        return response;
    }
}
