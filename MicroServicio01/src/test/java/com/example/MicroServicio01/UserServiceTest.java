package com.example.MicroServicio01;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.Optional;

import com.example.models.entities.User;
import com.example.models.requests.UserCreate;
import com.example.models.requests.UserUpdate;
import com.example.repositories.UserRepository;
import com.example.services.UserService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.server.ResponseStatusException;

public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testObtenerTodos() {
        User user1 = new User();
        User user2 = new User();

        when(userRepository.findAll()).thenReturn(Arrays.asList(user1, user2));

        assertEquals(2, userService.obtenerTodos().size());
    }

    @Test
    public void testObtenerPorId_Existente() {
        User user = new User();
        user.setId(1);

        when(userRepository.findById(1)).thenReturn(Optional.of(user));

        User result = userService.obtenerPorId(1);
        assertNotNull(result);
        assertEquals(1, result.getId());
    }

    @Test
    public void testObtenerPorId_NoExistente() {
        when(userRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(ResponseStatusException.class, () -> userService.obtenerPorId(1));
    }

    @Test
    public void testRegistrar_UsuarioExitoso() {
        UserCreate userCreate = new UserCreate();
        userCreate.setNombre("Javiera");
        userCreate.setEmail("javiera@example.com");
        userCreate.setPassword("123456");
        userCreate.setTelefono("123456789");

        User usuarioGuardado = new User();
        usuarioGuardado.setId(1);

        when(userRepository.existsByEmail(userCreate.getEmail())).thenReturn(false);
        when(userRepository.save(any(User.class))).thenReturn(usuarioGuardado);

        User result = userService.registrar(userCreate);
        assertNotNull(result);
        assertEquals(1, result.getId());
    }

    @Test
    public void testRegistrar_EmailDuplicado() {
        UserCreate userCreate = new UserCreate();
        userCreate.setNombre("Test");
        userCreate.setEmail("duplicado@example.com");
        userCreate.setPassword("123456");

        when(userRepository.existsByEmail(userCreate.getEmail())).thenReturn(true);

        assertThrows(ResponseStatusException.class, () -> userService.registrar(userCreate));
    }

    @Test
    public void testActualizar_UsuarioExistente() {
        User usuarioExistente = new User();
        usuarioExistente.setId(1);
        usuarioExistente.setNombre("NombreAntiguo");
        usuarioExistente.setTelefono("111");

        UserUpdate update = new UserUpdate();
        update.setId(1);
        update.setNombre("NombreNuevo");
        update.setTelefono("222");

        when(userRepository.findById(1)).thenReturn(Optional.of(usuarioExistente));
        when(userRepository.save(any(User.class))).thenReturn(usuarioExistente);

        User result = userService.actualizar(update);

        assertEquals("NombreNuevo", result.getNombre());
        assertEquals("222", result.getTelefono());
    }

    @Test
    public void testActualizar_UsuarioNoExistente() {
        UserUpdate update = new UserUpdate();
        update.setId(1);

        when(userRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(ResponseStatusException.class, () -> userService.actualizar(update));
    }

    @Test
    public void testEliminar_UsuarioExistente() {
        User usuario = new User();
        usuario.setId(1);

        when(userRepository.findById(1)).thenReturn(Optional.of(usuario));

        assertDoesNotThrow(() -> userService.eliminar(1));
        verify(userRepository, times(1)).delete(usuario);
    }

    @Test
    public void testEliminar_UsuarioNoExistente() {
        when(userRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(ResponseStatusException.class, () -> userService.eliminar(1));
    }

    @Test
    public void testHashearPassword() {
        String passwordPlano = "123456";
        String hashed = userService.hashearPassword(passwordPlano);

        assertNotNull(hashed);
        assertNotEquals(passwordPlano, hashed);
        assertTrue(hashed.startsWith("$2a$")); // Formato típico de BCrypt
    }
}
