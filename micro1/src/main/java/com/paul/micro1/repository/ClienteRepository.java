package com.paul.micro1.repository;


import com.paul.micro1.domain.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {

    Optional<Cliente> findByClienteId(String clienteId);

    boolean existsByClienteId(String clienteId);

    Optional<Cliente> findByIdentificacion(String identificacion);
}
