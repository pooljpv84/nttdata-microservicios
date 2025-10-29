package com.paul.micro2.repository;

import com.paul.micro2.domain.ClienteView;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ClienteViewRepository extends JpaRepository<ClienteView, Long> {
    Optional<ClienteView> findByClienteId(String clienteId);
}
