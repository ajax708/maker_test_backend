package com.example.backend.infrastructure.persistence.adapter;

import java.util.Optional;

import org.springframework.stereotype.Component;

import com.example.backend.application.port.out.RepositorioUsuarioPuerto;
import com.example.backend.domain.model.Usuario;
import com.example.backend.infrastructure.persistence.entity.UsuarioEntidad;
import com.example.backend.infrastructure.persistence.repository.RepositorioUsuarioJpa;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class AdaptadorRepositorioUsuario implements RepositorioUsuarioPuerto {

    private final RepositorioUsuarioJpa repositorioJpa;

    @Override
    public Usuario guardar(Usuario usuario) {
        UsuarioEntidad guardado = repositorioJpa.save(UsuarioEntidad.desdeDominio(usuario));
        return guardado.aDominio();
    }

    @Override
    public Optional<Usuario> buscarPorCorreo(String correo) {
        return repositorioJpa.findByCorreo(correo).map(UsuarioEntidad::aDominio);
    }

    @Override
    public Optional<Usuario> buscarPorId(Long id) {
        return repositorioJpa.findById(id).map(UsuarioEntidad::aDominio);
    }

    @Override
    public boolean existePorCorreo(String correo) {
        return repositorioJpa.existsByCorreo(correo);
    }
}
