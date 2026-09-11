package com.example.backend.infrastructure.web.dto.response;

public record RespuestaInicioSesion(String token, String correo, String rol) {
}
