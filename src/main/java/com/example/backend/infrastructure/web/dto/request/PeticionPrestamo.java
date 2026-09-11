package com.example.backend.infrastructure.web.dto.request;

import java.math.BigDecimal;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record PeticionPrestamo(
        @NotNull @DecimalMin(value = "0.01", message = "El monto debe ser mayor a 0")
        @DecimalMax(value = "100000", message = "El monto no puede superar 100000") BigDecimal monto,

        @NotNull @Min(value = 1, message = "El plazo minimo es 1 mes")
        @Max(value = 360, message = "El plazo maximo es 360 meses") Integer plazoMeses) {
}
