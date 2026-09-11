package com.example.backend.application.port.out;

import com.example.backend.domain.model.Usuario;

public interface GeneradorTokenPuerto {

    String generarToken(Usuario usuario);
}
