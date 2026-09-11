package com.example.backend.application.port.out;

import java.util.List;
import java.util.Optional;

import com.example.backend.domain.model.Prestamo;

public interface RepositorioPrestamoPuerto {

    Prestamo guardar(Prestamo prestamo);

    Optional<Prestamo> buscarPorId(Long id);

    List<Prestamo> buscarPorUsuario(Long idUsuario);

    List<Prestamo> buscarTodos();
}
