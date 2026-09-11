package com.example.backend.domain.exception;

public class OperacionInvalidaExcepcion extends RuntimeException {
    public OperacionInvalidaExcepcion(String mensaje) {
        super(mensaje);
    }
}
