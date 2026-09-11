package com.example.backend.infrastructure.persistence.adapter;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;

import com.example.backend.application.port.out.RepositorioPrestamoPuerto;
import com.example.backend.domain.model.Prestamo;
import com.example.backend.infrastructure.persistence.entity.PrestamoEntidad;
import com.example.backend.infrastructure.persistence.repository.RepositorioPrestamoJpa;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class AdaptadorRepositorioPrestamo implements RepositorioPrestamoPuerto {

    private final RepositorioPrestamoJpa repositorioJpa;

    @Override
    public Prestamo guardar(Prestamo prestamo) {
        PrestamoEntidad guardado = repositorioJpa.save(PrestamoEntidad.desdeDominio(prestamo));
        return guardado.aDominio();
    }

    @Override
    public Optional<Prestamo> buscarPorId(Long id) {
        return repositorioJpa.findById(id).map(PrestamoEntidad::aDominio);
    }

    @Override
    public List<Prestamo> buscarPorUsuario(Long idUsuario) {
        return repositorioJpa.findByIdUsuario(idUsuario).stream()
                .map(PrestamoEntidad::aDominio)
                .toList();
    }

    @Override
    public List<Prestamo> buscarTodos() {
        return repositorioJpa.findAll().stream()
                .map(PrestamoEntidad::aDominio)
                .toList();
    }
}
