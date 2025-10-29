package com.paul.micro2.domain;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "movimiento")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Movimiento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "cuenta_id", nullable = false)
    private Long cuentaId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 12)
    private TipoMovimiento tipo;

    @Column(nullable = false, precision = 18, scale = 2)
    private BigDecimal monto;

    @Column(length = 100)
    private String referencia;

    @Column(nullable = false, updatable = false)
    private LocalDateTime fechaCreacion;

    @PrePersist
    void pre() {
        if (fechaCreacion == null) fechaCreacion = LocalDateTime.now();
    }
}
