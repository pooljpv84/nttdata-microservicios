package com.paul.micro1.repository;

import com.paul.micro1.domain.Persona;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PersonaRepository extends JpaRepository<Persona, Long>
{
    //evitar NullPointerException
    Optional<Persona> findByIdentificacion(String identificacion);
    boolean existsByIdentificacion(String identificacion);
}
