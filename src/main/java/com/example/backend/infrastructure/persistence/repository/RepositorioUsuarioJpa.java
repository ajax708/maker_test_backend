package com.example.backend.infrastructure.persistence.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.backend.infrastructure.persistence.entity.UsuarioEntidad;

public interface RepositorioUsuarioJpa extends JpaRepository<UsuarioEntidad, Long> {

    Optional<UsuarioEntidad> findByCorreo(String correo);

    boolean existsByCorreo(String correo);
}
