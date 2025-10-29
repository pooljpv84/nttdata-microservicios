package com.paul.micro2.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Table(name = "cliente_view")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ClienteView {
    @Id
    private Long id; // mismo PK que micro1

    @Column(nullable = false, unique = true, length = 50)
    private String clienteId;

    @Column(nullable = false, length = 20)
    private String identificacion;

    @Column(nullable = false, length = 100)
    private String nombre;

    @Column(nullable = false)
    private Boolean estado;
}