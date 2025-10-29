package com.paul.micro2.repository;

import com.paul.micro2.domain.Movimiento;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface MovimientoRepository extends JpaRepository<Movimiento, Long> {
    List<Movimiento> findByCuentaIdOrderByFechaCreacionDesc(Long cuentaId);
    List<Movimiento> findByCuentaIdAndFechaCreacionGreaterThanEqual(Long cuentaId, LocalDateTime fecha);
    List<Movimiento> findByCuentaIdAndFechaCreacionBetweenOrderByFechaCreacionAsc(Long cuentaId, LocalDateTime desde, LocalDateTime hasta);
}
