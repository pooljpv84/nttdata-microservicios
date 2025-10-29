package com.paul.micro1.domain;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "cliente")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@PrimaryKeyJoinColumn(name = "id") // id = FK a persona.id (JOINED)
public class Cliente extends Persona {

    @Column(name = "clienteid", nullable = false, unique = true, length = 50)
    private String clienteId;

    @Column(name = "contrasenia", nullable = false, length = 255)
    private String contrasenia;

    @Column(nullable = false)
    @Builder.Default
    private Boolean estado = true;
}
