package com.example.backend.infrastructure.persistence.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.backend.infrastructure.persistence.entity.PrestamoEntidad;

public interface RepositorioPrestamoJpa extends JpaRepository<PrestamoEntidad, Long> {

    List<PrestamoEntidad> findByIdUsuario(Long idUsuario);
}
