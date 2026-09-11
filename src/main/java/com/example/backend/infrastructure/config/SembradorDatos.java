package com.example.backend.infrastructure.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.example.backend.application.port.out.RepositorioUsuarioPuerto;
import com.example.backend.domain.model.Rol;
import com.example.backend.domain.model.Usuario;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class SembradorDatos implements CommandLineRunner {

    private final RepositorioUsuarioPuerto repositorioUsuario;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        sembrarSiNoExiste("usuario@test.com", "123", Rol.USUARIO);
        sembrarSiNoExiste("admin@test.com", "123", Rol.ADMINISTRADOR);
    }

    private void sembrarSiNoExiste(String correo, String contrasenaPlana, Rol rol) {
        if (repositorioUsuario.existePorCorreo(correo)) {
            return;
        }
        Usuario usuario = Usuario.builder()
                .correo(correo)
                .contrasenaHash(passwordEncoder.encode(contrasenaPlana))
                .rol(rol)
                .build();
        repositorioUsuario.guardar(usuario);
        log.info("Usuario de prueba sembrado: {} ({})", correo, rol);
    }
}
