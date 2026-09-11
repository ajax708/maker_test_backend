package com.example.backend.infrastructure.web.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.backend.application.port.in.PrestamoCasoUso;
import com.example.backend.application.port.in.UsuarioCasoUso;
import com.example.backend.domain.model.Prestamo;
import com.example.backend.domain.model.Usuario;
import com.example.backend.infrastructure.security.PrincipalUsuarioAutenticado;
import com.example.backend.infrastructure.web.dto.request.PeticionPrestamo;
import com.example.backend.infrastructure.web.dto.response.RespuestaPrestamo;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/loans")
@RequiredArgsConstructor
public class ControladorPrestamo {

    private final PrestamoCasoUso prestamoCasoUso;
    private final UsuarioCasoUso usuarioCasoUso;

    @PostMapping
    public ResponseEntity<RespuestaPrestamo> solicitar(@Valid @RequestBody PeticionPrestamo peticion,
                                                         @AuthenticationPrincipal PrincipalUsuarioAutenticado principal) {
        Prestamo prestamo = prestamoCasoUso.solicitar(
                principal.getUsuario().getId(), peticion.monto(), peticion.plazoMeses());
        return ResponseEntity.status(HttpStatus.CREATED).body(RespuestaPrestamo.desde(prestamo));
    }

    @GetMapping("/me")
    public List<RespuestaPrestamo> misPrestamos(@AuthenticationPrincipal PrincipalUsuarioAutenticado principal) {
        return prestamoCasoUso.listarPorUsuario(principal.getUsuario().getId()).stream()
                .map(RespuestaPrestamo::desde)
                .toList();
    }

    @GetMapping
    public List<RespuestaPrestamo> todos() {
        return prestamoCasoUso.listarTodos().stream()
                .map(prestamo -> RespuestaPrestamo.desde(prestamo, correoDe(prestamo.getIdUsuario())))
                .toList();
    }

    private String correoDe(Long idUsuario) {
        return usuarioCasoUso.buscarPorId(idUsuario).map(Usuario::getCorreo).orElse(null);
    }

    @PatchMapping("/{id}/approve")
    public RespuestaPrestamo aprobar(@PathVariable Long id,
                                      @AuthenticationPrincipal PrincipalUsuarioAutenticado principal) {
        return RespuestaPrestamo.desde(prestamoCasoUso.aprobar(id, principal.getUsuario().getId()));
    }

    @PatchMapping("/{id}/reject")
    public RespuestaPrestamo rechazar(@PathVariable Long id,
                                       @AuthenticationPrincipal PrincipalUsuarioAutenticado principal) {
        return RespuestaPrestamo.desde(prestamoCasoUso.rechazar(id, principal.getUsuario().getId()));
    }
}
