package com.paul.micro1.domain;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "persona")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Inheritance(strategy = InheritanceType.JOINED) // PK compartida con Cliente

public class Persona
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String nombre;
    @Column(length = 20)
    private String genero;
    private Integer edad;

    @Column(nullable = false, unique = true, length = 20)
    private String identificacion;

    @Column(length = 255)
    private String direccion;
    @Column(length = 20)
    private String telefono;
}
