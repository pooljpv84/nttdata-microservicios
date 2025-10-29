package com.paul.micro2.dto;

import java.math.BigDecimal;

public record MovimientoCreateResult(
        String numeroCuenta,
        String tipoCuenta,
        BigDecimal saldoInicial,
        BigDecimal saldoDisponible,
        Boolean estadoCuenta,
        MovimientoResponse movimiento
) {}
