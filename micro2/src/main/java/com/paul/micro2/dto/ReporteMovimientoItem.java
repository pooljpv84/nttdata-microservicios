package com.paul.micro2.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public class ReporteMovimientoItem {
    @JsonProperty("Fecha")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime fecha;

    @JsonProperty("Cliente")
    private String cliente;

    @JsonProperty("Numero Cuenta")
    private String numeroCuenta;

    @JsonProperty("Tipo")
    private String tipoCuenta;

    @JsonProperty("Saldo Inicial")
    private BigDecimal saldoInicial;

    @JsonProperty("Estado")
    private Boolean estadoCuenta;

    @JsonProperty("Movimiento")
    private BigDecimal movimiento;

    @JsonProperty("Saldo Disponible")
    private BigDecimal saldoDisponible;

    public ReporteMovimientoItem(LocalDateTime fecha, String cliente, String numeroCuenta, String tipoCuenta,
                                 BigDecimal saldoInicial, Boolean estadoCuenta, BigDecimal movimiento, BigDecimal saldoDisponible) {
        this.fecha = fecha;
        this.cliente = cliente;
        this.numeroCuenta = numeroCuenta;
        this.tipoCuenta = tipoCuenta;
        this.saldoInicial = saldoInicial;
        this.estadoCuenta = estadoCuenta;
        this.movimiento = movimiento;
        this.saldoDisponible = saldoDisponible;
    }

    public LocalDateTime getFecha() { return fecha; }
    public String getCliente() { return cliente; }
    public String getNumeroCuenta() { return numeroCuenta; }
    public String getTipoCuenta() { return tipoCuenta; }
    public BigDecimal getSaldoInicial() { return saldoInicial; }
    public Boolean getEstadoCuenta() { return estadoCuenta; }
    public BigDecimal getMovimiento() { return movimiento; }
    public BigDecimal getSaldoDisponible() { return saldoDisponible; }
}
