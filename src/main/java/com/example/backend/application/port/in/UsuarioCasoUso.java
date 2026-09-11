package com.example.backend.application.port.in;

import java.util.Optional;

import com.example.backend.domain.model.Usuario;

public interface UsuarioCasoUso {

    Usuario registrar(String correo, String contrasenaPlana);

    ResultadoAutenticacion autenticar(String correo, String contrasena);

    Optional<Usuario> buscarPorId(Long id);
}
