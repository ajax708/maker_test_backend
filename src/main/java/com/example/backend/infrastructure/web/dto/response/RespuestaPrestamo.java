package com.example.backend.infrastructure.web.dto.response;

import java.math.BigDecimal;
import java.time.Instant;

import com.example.backend.domain.model.Prestamo;

public record RespuestaPrestamo(
        Long id,
        Long idUsuario,
        String correoUsuario,
        BigDecimal monto,
        Integer plazoMeses,
        String estado,
        Instant fechaSolicitud,
        Instant fechaResolucion,
        Long resueltoPorId) {

    public static RespuestaPrestamo desde(Prestamo prestamo) {
        return desde(prestamo, null);
    }

    public static RespuestaPrestamo desde(Prestamo prestamo, String correoUsuario) {
        return new RespuestaPrestamo(
                prestamo.getId(),
                prestamo.getIdUsuario(),
                correoUsuario,
                prestamo.getMonto(),
                prestamo.getPlazoMeses(),
                prestamo.getEstado().name(),
                prestamo.getFechaSolicitud(),
                prestamo.getFechaResolucion(),
                prestamo.getResueltoPorId());
    }
}
