package com.paul.micro2.dto;

import com.paul.micro2.domain.TipoMovimiento;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record MovimientoResponse(
        Long id,
        Long cuentaId,
        TipoMovimiento tipo,
        BigDecimal monto,
        String referencia,
        LocalDateTime fechaCreacion
) {
}
