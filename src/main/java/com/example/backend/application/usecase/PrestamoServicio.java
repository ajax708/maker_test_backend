package com.example.backend.application.usecase;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.example.backend.application.port.in.PrestamoCasoUso;
import com.example.backend.application.port.out.RepositorioPrestamoPuerto;
import com.example.backend.domain.exception.RecursoNoEncontradoExcepcion;
import com.example.backend.domain.model.EstadoPrestamo;
import com.example.backend.domain.model.Prestamo;
import com.example.backend.domain.service.PoliticaPrestamo;
import com.example.backend.infrastructure.config.ConfiguracionCache;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PrestamoServicio implements PrestamoCasoUso {

    private final RepositorioPrestamoPuerto repositorioPrestamo;
    private final PoliticaPrestamo politicaPrestamo = new PoliticaPrestamo();

    @Override
    @CacheEvict(cacheNames = ConfiguracionCache.CACHE_PRESTAMOS_POR_USUARIO, key = "#idUsuario")
    public Prestamo solicitar(Long idUsuario, BigDecimal monto, Integer plazoMeses) {
        politicaPrestamo.validarSolicitud(monto, plazoMeses);

        Prestamo prestamo = Prestamo.builder()
                .idUsuario(idUsuario)
                .monto(monto)
                .plazoMeses(plazoMeses)
                .estado(EstadoPrestamo.PENDIENTE)
                .fechaSolicitud(Instant.now())
                .build();

        return repositorioPrestamo.guardar(prestamo);
    }

    @Override
    @Cacheable(cacheNames = ConfiguracionCache.CACHE_PRESTAMOS_POR_USUARIO, key = "#idUsuario")
    public List<Prestamo> listarPorUsuario(Long idUsuario) {
        return repositorioPrestamo.buscarPorUsuario(idUsuario);
    }

    @Override
    public List<Prestamo> listarTodos() {
        return repositorioPrestamo.buscarTodos();
    }

    @Override
    @Transactional
    @CacheEvict(cacheNames = ConfiguracionCache.CACHE_PRESTAMOS_POR_USUARIO, key = "#result.idUsuario")
    public Prestamo aprobar(Long idPrestamo, Long idAdministrador) {
        return resolver(idPrestamo, idAdministrador, EstadoPrestamo.APROBADO);
    }

    @Override
    @Transactional
    @CacheEvict(cacheNames = ConfiguracionCache.CACHE_PRESTAMOS_POR_USUARIO, key = "#result.idUsuario")
    public Prestamo rechazar(Long idPrestamo, Long idAdministrador) {
        return resolver(idPrestamo, idAdministrador, EstadoPrestamo.RECHAZADO);
    }

    private Prestamo resolver(Long idPrestamo, Long idAdministrador, EstadoPrestamo estadoFinal) {
        Prestamo prestamo = repositorioPrestamo.buscarPorId(idPrestamo)
                .orElseThrow(() -> new RecursoNoEncontradoExcepcion("No se encontro el prestamo " + idPrestamo));

        politicaPrestamo.validarTransicionResolucion(prestamo);

        prestamo.setEstado(estadoFinal);
        prestamo.setFechaResolucion(Instant.now());
        prestamo.setResueltoPorId(idAdministrador);

        return repositorioPrestamo.guardar(prestamo);
    }
}
