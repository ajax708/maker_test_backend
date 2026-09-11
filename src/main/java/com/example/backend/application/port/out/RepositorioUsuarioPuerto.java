package com.example.backend.application.port.out;

import java.util.Optional;

import com.example.backend.domain.model.Usuario;

public interface RepositorioUsuarioPuerto {

    Usuario guardar(Usuario usuario);

    Optional<Usuario> buscarPorCorreo(String correo);

    Optional<Usuario> buscarPorId(Long id);

    boolean existePorCorreo(String correo);
}
