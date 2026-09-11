package com.example.backend.infrastructure.web.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record PeticionInicioSesion(
        @NotBlank @Email String correo,
        @NotBlank String contrasena) {
}
