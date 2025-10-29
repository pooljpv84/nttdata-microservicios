package com.paul.micro2.domain;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "cuenta")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Cuenta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "cliente_id", nullable = false)
    private Long clienteId; // PK del cliente en micro1

    @Column(name = "cliente_id_cod", nullable = false, length = 50)
    private String clienteIdCod;
    // Código externo del cliente (no PK)

    @Column(name = "numero", nullable = false, unique = true, length = 30)
    private String numero;

    @Column(nullable = false, length = 20)
    private String tipo; // AHO/CTE/

    @Column(nullable = false)
    private Boolean activa = true;

    @Column(nullable = false, precision = 18, scale = 2)
    private BigDecimal saldo = BigDecimal.ZERO;
}
