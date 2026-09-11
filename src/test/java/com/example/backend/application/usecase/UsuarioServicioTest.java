package com.example.backend.application.usecase;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.example.backend.application.port.in.ResultadoAutenticacion;
import com.example.backend.application.port.out.GeneradorTokenPuerto;
import com.example.backend.application.port.out.RepositorioUsuarioPuerto;
import com.example.backend.domain.exception.CredencialesInvalidasExcepcion;
import com.example.backend.domain.model.Rol;
import com.example.backend.domain.model.Usuario;

@ExtendWith(MockitoExtension.class)
class UsuarioServicioTest {

    @Mock
    private RepositorioUsuarioPuerto repositorioUsuario;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private GeneradorTokenPuerto generadorToken;

    private UsuarioServicio servicio;

    @BeforeEach
    void configurar() {
        servicio = new UsuarioServicio(repositorioUsuario, passwordEncoder, generadorToken);
    }

    @Test
    void devuelveUnTokenConCredencialesValidas() {
        Usuario usuario = Usuario.builder().id(1L).correo("usuario@test.com").contrasenaHash("hash").rol(Rol.USUARIO).build();
        when(repositorioUsuario.buscarPorCorreo("usuario@test.com")).thenReturn(Optional.of(usuario));
        when(passwordEncoder.matches("123", "hash")).thenReturn(true);
        when(generadorToken.generarToken(usuario)).thenReturn("token-de-prueba");

        ResultadoAutenticacion resultado = servicio.autenticar("usuario@test.com", "123");

        assertThat(resultado.token()).isEqualTo("token-de-prueba");
        assertThat(resultado.rol()).isEqualTo(Rol.USUARIO);
    }

    @Test
    void lanzaExcepcionSiElUsuarioNoExiste() {
        when(repositorioUsuario.buscarPorCorreo(any())).thenReturn(Optional.empty());

        assertThatThrownBy(() -> servicio.autenticar("nadie@test.com", "123"))
                .isInstanceOf(CredencialesInvalidasExcepcion.class);
    }

    @Test
    void lanzaExcepcionSiLaContrasenaNoCoincide() {
        Usuario usuario = Usuario.builder().id(1L).correo("usuario@test.com").contrasenaHash("hash").rol(Rol.USUARIO).build();
        when(repositorioUsuario.buscarPorCorreo("usuario@test.com")).thenReturn(Optional.of(usuario));
        when(passwordEncoder.matches("mala", "hash")).thenReturn(false);

        assertThatThrownBy(() -> servicio.autenticar("usuario@test.com", "mala"))
                .isInstanceOf(CredencialesInvalidasExcepcion.class);
    }
}
