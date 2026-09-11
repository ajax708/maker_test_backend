package com.example.backend.application.port.in;

import java.math.BigDecimal;
import java.util.List;

import com.example.backend.domain.model.Prestamo;

public interface PrestamoCasoUso {

    Prestamo solicitar(Long idUsuario, BigDecimal monto, Integer plazoMeses);

    List<Prestamo> listarPorUsuario(Long idUsuario);

    List<Prestamo> listarTodos();

    Prestamo aprobar(Long idPrestamo, Long idAdministrador);

    Prestamo rechazar(Long idPrestamo, Long idAdministrador);
}
