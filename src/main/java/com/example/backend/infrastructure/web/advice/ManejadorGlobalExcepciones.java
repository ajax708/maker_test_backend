package com.example.backend.infrastructure.web.advice;

import java.util.stream.Collectors;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.example.backend.domain.exception.CredencialesInvalidasExcepcion;
import com.example.backend.domain.exception.OperacionInvalidaExcepcion;
import com.example.backend.domain.exception.RecursoNoEncontradoExcepcion;

@RestControllerAdvice
public class ManejadorGlobalExcepciones extends ResponseEntityExceptionHandler {

    @ExceptionHandler(CredencialesInvalidasExcepcion.class)
    public ProblemDetail manejarCredencialesInvalidas(CredencialesInvalidasExcepcion ex) {
        return problema(HttpStatus.UNAUTHORIZED, ex.getMessage());
    }

    @ExceptionHandler(RecursoNoEncontradoExcepcion.class)
    public ProblemDetail manejarNoEncontrado(RecursoNoEncontradoExcepcion ex) {
        return problema(HttpStatus.NOT_FOUND, ex.getMessage());
    }

    @ExceptionHandler(OperacionInvalidaExcepcion.class)
    public ProblemDetail manejarConflicto(OperacionInvalidaExcepcion ex) {
        return problema(HttpStatus.CONFLICT, ex.getMessage());
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ProblemDetail manejarArgumentoInvalido(IllegalArgumentException ex) {
        return problema(HttpStatus.BAD_REQUEST, ex.getMessage());
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            @NonNull MethodArgumentNotValidException ex, @NonNull HttpHeaders headers,
            @NonNull HttpStatusCode status, @NonNull WebRequest request) {
        String detalle = ex.getBindingResult().getFieldErrors().stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .collect(Collectors.joining("; "));
        return ResponseEntity.badRequest().body(problema(HttpStatus.BAD_REQUEST, detalle));
    }

    private ProblemDetail problema(HttpStatus status, String mensaje) {
        return ProblemDetail.forStatusAndDetail(status, mensaje);
    }
}
