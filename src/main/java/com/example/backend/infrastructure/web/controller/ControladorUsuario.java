package com.example.backend.infrastructure.web.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.backend.application.port.in.UsuarioCasoUso;
import com.example.backend.domain.model.Usuario;
import com.example.backend.infrastructure.web.dto.request.PeticionRegistroUsuario;
import com.example.backend.infrastructure.web.dto.response.RespuestaUsuario;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class ControladorUsuario {

    private final UsuarioCasoUso usuarioCasoUso;

    @PostMapping
    public ResponseEntity<RespuestaUsuario> registrar(@Valid @RequestBody PeticionRegistroUsuario peticion) {
        Usuario usuario = usuarioCasoUso.registrar(peticion.correo(), peticion.contrasena());
        RespuestaUsuario respuesta = new RespuestaUsuario(usuario.getId(), usuario.getCorreo(), usuario.getRol().name());
        return ResponseEntity.status(HttpStatus.CREATED).body(respuesta);
    }
}
