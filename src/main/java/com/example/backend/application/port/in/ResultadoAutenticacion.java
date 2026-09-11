package com.example.backend.application.port.in;

import com.example.backend.domain.model.Rol;

public record ResultadoAutenticacion(String token, String correo, Rol rol) {
}
