package com.example.backend.infrastructure.web.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.backend.application.port.in.ResultadoAutenticacion;
import com.example.backend.application.port.in.UsuarioCasoUso;
import com.example.backend.infrastructure.web.dto.request.PeticionInicioSesion;
import com.example.backend.infrastructure.web.dto.response.RespuestaInicioSesion;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class ControladorAutenticacion {

    private final UsuarioCasoUso usuarioCasoUso;

    @PostMapping("/login")
    public ResponseEntity<RespuestaInicioSesion> login(@Valid @RequestBody PeticionInicioSesion peticion) {
        ResultadoAutenticacion resultado = usuarioCasoUso.autenticar(peticion.correo(), peticion.contrasena());
        return ResponseEntity.ok(new RespuestaInicioSesion(resultado.token(), resultado.correo(), resultado.rol().name()));
    }
}
