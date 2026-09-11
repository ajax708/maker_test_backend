package com.example.backend.domain.exception;

public class CredencialesInvalidasExcepcion extends RuntimeException {

    public CredencialesInvalidasExcepcion() {
        super("Correo o contrasena invalidos");
    }
}
