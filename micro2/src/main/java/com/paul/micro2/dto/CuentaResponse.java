package com.paul.micro2.dto;

import java.math.BigDecimal;

public record CuentaResponse(
        Long id,
        String numero,
        String tipo,
        Boolean activa,
        BigDecimal saldo,
        Long clienteId, //pk
        String clienteIdCod, //codigo
        String clienteNombre // nombre del cliente
) {
}
