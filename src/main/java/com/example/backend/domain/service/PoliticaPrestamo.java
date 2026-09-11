package com.example.backend.domain.service;

import java.math.BigDecimal;
import com.example.backend.domain.exception.OperacionInvalidaExcepcion;
import com.example.backend.domain.model.EstadoPrestamo;
import com.example.backend.domain.model.Prestamo;

public class PoliticaPrestamo {

    public static final BigDecimal MONTO_MINIMO = BigDecimal.ZERO;
    public static final BigDecimal MONTO_MAXIMO = new BigDecimal("100000");
    public static final int PLAZO_MINIMO_MESES = 1;
    public static final int PLAZO_MAXIMO_MESES = 360;

    public void validarSolicitud(BigDecimal monto, Integer plazoMeses) {
        if (monto == null || monto.compareTo(MONTO_MINIMO) <= 0) {
            throw new IllegalArgumentException("El monto debe ser mayor a 0");
        }
        if (monto.compareTo(MONTO_MAXIMO) > 0) {
            throw new IllegalArgumentException("El monto no puede superar " + MONTO_MAXIMO);
        }
        if (plazoMeses == null || plazoMeses < PLAZO_MINIMO_MESES || plazoMeses > PLAZO_MAXIMO_MESES) {
            throw new IllegalArgumentException(
                    "El plazo debe estar entre " + PLAZO_MINIMO_MESES + " y " + PLAZO_MAXIMO_MESES + " meses");
        }
    }

    public void validarTransicionResolucion(Prestamo prestamo) {
        if (prestamo.getEstado() != EstadoPrestamo.PENDIENTE) {
            throw new OperacionInvalidaExcepcion(
                    "El prestamo " + prestamo.getId() + " ya esta " + prestamo.getEstado());
        }
    }
}
