package com.example.backend.infrastructure.persistence.entity;

import java.math.BigDecimal;
import java.time.Instant;

import com.example.backend.domain.model.EstadoPrestamo;
import com.example.backend.domain.model.Prestamo;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "prestamos")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PrestamoEntidad {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "id_usuario", nullable = false)
    private Long idUsuario;

    @Column(nullable = false, precision = 15, scale = 2)
    private BigDecimal monto;

    @Column(name = "plazo_meses", nullable = false)
    private Integer plazoMeses;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EstadoPrestamo estado;

    @Column(name = "fecha_solicitud", nullable = false)
    private Instant fechaSolicitud;

    @Column(name = "fecha_resolucion")
    private Instant fechaResolucion;

    @Column(name = "resuelto_por_id")
    private Long resueltoPorId;

    public static PrestamoEntidad desdeDominio(Prestamo prestamo) {
        PrestamoEntidad entidad = new PrestamoEntidad();
        entidad.setId(prestamo.getId());
        entidad.setIdUsuario(prestamo.getIdUsuario());
        entidad.setMonto(prestamo.getMonto());
        entidad.setPlazoMeses(prestamo.getPlazoMeses());
        entidad.setEstado(prestamo.getEstado());
        entidad.setFechaSolicitud(prestamo.getFechaSolicitud());
        entidad.setFechaResolucion(prestamo.getFechaResolucion());
        entidad.setResueltoPorId(prestamo.getResueltoPorId());
        return entidad;
    }

    public Prestamo aDominio() {
        return Prestamo.builder()
                .id(id)
                .idUsuario(idUsuario)
                .monto(monto)
                .plazoMeses(plazoMeses)
                .estado(estado)
                .fechaSolicitud(fechaSolicitud)
                .fechaResolucion(fechaResolucion)
                .resueltoPorId(resueltoPorId)
                .build();
    }
}
