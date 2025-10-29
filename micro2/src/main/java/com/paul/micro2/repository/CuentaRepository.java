package com.paul.micro2.repository;

import com.paul.micro2.domain.Cuenta;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CuentaRepository extends JpaRepository<Cuenta, Long> {
    boolean existsByNumero(String numero);
    Optional<Cuenta> findByNumero(String numero);
    List<Cuenta> findByClienteId(Long clienteId);        // por PK
    List<Cuenta> findByClienteIdCod(String clienteIdCod); // por código externo
}
