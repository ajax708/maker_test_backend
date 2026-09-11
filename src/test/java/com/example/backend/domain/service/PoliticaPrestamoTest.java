package com.example.backend.domain.service;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.math.BigDecimal;
import java.time.Instant;

import org.junit.jupiter.api.Test;

import com.example.backend.domain.exception.OperacionInvalidaExcepcion;
import com.example.backend.domain.model.EstadoPrestamo;
import com.example.backend.domain.model.Prestamo;

class PoliticaPrestamoTest {

    private final PoliticaPrestamo politica = new PoliticaPrestamo();

    @Test
    void rechazaMontoMenorOIgualACero() {
        assertThatThrownBy(() -> politica.validarSolicitud(BigDecimal.ZERO, 12))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void rechazaMontoMayorAlMaximo() {
        assertThatThrownBy(() -> politica.validarSolicitud(new BigDecimal("999999"), 12))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void rechazaPlazoFueraDeRango() {
        assertThatThrownBy(() -> politica.validarSolicitud(BigDecimal.valueOf(1000), 0))
                .isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> politica.validarSolicitud(BigDecimal.valueOf(1000), 400))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void aceptaUnaSolicitudValida() {
        assertThatCode(() -> politica.validarSolicitud(BigDecimal.valueOf(1000), 12))
                .doesNotThrowAnyException();
    }

    @Test
    void permiteResolverUnPrestamoPendiente() {
        Prestamo prestamo = prestamoConEstado(EstadoPrestamo.PENDIENTE);
        assertThatCode(() -> politica.validarTransicionResolucion(prestamo)).doesNotThrowAnyException();
    }

    @Test
    void rechazaResolverUnPrestamoYaAprobado() {
        Prestamo prestamo = prestamoConEstado(EstadoPrestamo.APROBADO);
        assertThatThrownBy(() -> politica.validarTransicionResolucion(prestamo))
                .isInstanceOf(OperacionInvalidaExcepcion.class);
    }

    private Prestamo prestamoConEstado(EstadoPrestamo estado) {
        return Prestamo.builder()
                .id(1L)
                .idUsuario(10L)
                .monto(BigDecimal.valueOf(1000))
                .plazoMeses(12)
                .estado(estado)
                .fechaSolicitud(Instant.now())
                .build();
    }
}
