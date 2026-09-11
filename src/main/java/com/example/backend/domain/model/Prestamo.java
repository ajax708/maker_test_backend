package com.example.backend.domain.model;

import java.math.BigDecimal;
import java.time.Instant;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Prestamo {

    private Long id;
    private Long idUsuario;
    private BigDecimal monto;
    private Integer plazoMeses;
    private EstadoPrestamo estado;
    private Instant fechaSolicitud;
    private Instant fechaResolucion;
    private Long resueltoPorId;
}
