package com.example.backend.infrastructure.web.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record PeticionRegistroUsuario(
        @NotBlank @Email String correo,
        @NotBlank @Size(min = 3, message = "La contrasena debe tener al menos 3 caracteres") String contrasena) {
}
