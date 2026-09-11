package com.example.backend.application.usecase;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.backend.application.port.out.RepositorioPrestamoPuerto;
import com.example.backend.domain.exception.OperacionInvalidaExcepcion;
import com.example.backend.domain.exception.RecursoNoEncontradoExcepcion;
import com.example.backend.domain.model.EstadoPrestamo;
import com.example.backend.domain.model.Prestamo;

@ExtendWith(MockitoExtension.class)
class PrestamoServicioTest {

    @Mock
    private RepositorioPrestamoPuerto repositorioPrestamo;

    private PrestamoServicio servicio;

    @BeforeEach
    void configurar() {
        servicio = new PrestamoServicio(repositorioPrestamo);
    }

    @Test
    void creaElPrestamoEnEstadoPendiente() {
        when(repositorioPrestamo.guardar(any())).thenAnswer(invocacion -> invocacion.getArgument(0));

        Prestamo resultado = servicio.solicitar(10L, BigDecimal.valueOf(1000), 12);

        assertThat(resultado.getEstado()).isEqualTo(EstadoPrestamo.PENDIENTE);
        assertThat(resultado.getIdUsuario()).isEqualTo(10L);
    }

    @Test
    void rechazaUnMontoInvalido() {
        assertThatThrownBy(() -> servicio.solicitar(10L, BigDecimal.valueOf(-100), 12))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void apruebaUnPrestamoPendiente() {
        Prestamo pendiente = prestamoConEstado(EstadoPrestamo.PENDIENTE);
        when(repositorioPrestamo.buscarPorId(1L)).thenReturn(Optional.of(pendiente));
        when(repositorioPrestamo.guardar(any())).thenAnswer(invocacion -> invocacion.getArgument(0));

        Prestamo resultado = servicio.aprobar(1L, 99L);

        assertThat(resultado.getEstado()).isEqualTo(EstadoPrestamo.APROBADO);
        assertThat(resultado.getResueltoPorId()).isEqualTo(99L);
        verify(repositorioPrestamo).guardar(any());
    }

    @Test
    void rechazaUnPrestamoPendiente() {
        Prestamo pendiente = prestamoConEstado(EstadoPrestamo.PENDIENTE);
        when(repositorioPrestamo.buscarPorId(1L)).thenReturn(Optional.of(pendiente));
        when(repositorioPrestamo.guardar(any())).thenAnswer(invocacion -> invocacion.getArgument(0));

        Prestamo resultado = servicio.rechazar(1L, 99L);

        assertThat(resultado.getEstado()).isEqualTo(EstadoPrestamo.RECHAZADO);
    }

    @Test
    void noPermiteResolverUnPrestamoYaResuelto() {
        Prestamo yaAprobado = prestamoConEstado(EstadoPrestamo.APROBADO);
        when(repositorioPrestamo.buscarPorId(1L)).thenReturn(Optional.of(yaAprobado));

        assertThatThrownBy(() -> servicio.aprobar(1L, 99L))
                .isInstanceOf(OperacionInvalidaExcepcion.class);

        verify(repositorioPrestamo, never()).guardar(any());
    }

    @Test
    void lanzaExcepcionSiElPrestamoNoExiste() {
        when(repositorioPrestamo.buscarPorId(1L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> servicio.aprobar(1L, 99L))
                .isInstanceOf(RecursoNoEncontradoExcepcion.class);
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
