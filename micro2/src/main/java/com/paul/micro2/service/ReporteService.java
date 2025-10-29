package com.paul.micro2.service;

import com.paul.micro2.domain.Cuenta;
import com.paul.micro2.domain.Movimiento;
import com.paul.micro2.domain.TipoMovimiento;
import com.paul.micro2.dto.ReporteMovimientoItem;
import com.paul.micro2.exception.NotFoundException;
import com.paul.micro2.repository.ClienteViewRepository;
import com.paul.micro2.repository.CuentaRepository;
import com.paul.micro2.repository.MovimientoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReporteService {
    private final CuentaRepository cuentaRepo;
    private final MovimientoRepository movRepo;
    private final ClienteViewRepository clienteRepo;

    public List<ReporteMovimientoItem> estadoCuenta(Long clienteId, LocalDate desdeFecha, LocalDate hastaFecha) {
        if (clienteId == null) throw new IllegalArgumentException("clienteId requerido");
        var cliente = clienteRepo.findById(clienteId).orElseThrow(() -> new NotFoundException("Cliente no existe"));
        String clienteNombre = cliente.getNombre();

        LocalDateTime desde = desdeFecha == null ? LocalDate.MIN.atStartOfDay() : desdeFecha.atStartOfDay();
        LocalDateTime hasta = hastaFecha == null ? LocalDate.MAX.atTime(LocalTime.MAX) : hastaFecha.atTime(LocalTime.MAX);

        List<Cuenta> cuentas = cuentaRepo.findByClienteId(clienteId);
        List<ReporteMovimientoItem> items = new ArrayList<>();

        for (Cuenta c : cuentas) {
            // 1) Calcular saldo al inicio del rango
            BigDecimal saldo = c.getSaldo(); // saldo actual
            List<Movimiento> movsDesde = movRepo.findByCuentaIdAndFechaCreacionGreaterThanEqual(c.getId(), desde);
            // Revertimos todos los movimientos desde 'desde' hasta ahora para obtener saldo al inicio
            movsDesde.sort(Comparator.comparing(Movimiento::getFechaCreacion));
            for (Movimiento m : movsDesde) {
                if (m.getTipo() == TipoMovimiento.DEPOSITO) {
                    saldo = saldo.subtract(m.getMonto());
                } else { // RETIRO
                    saldo = saldo.add(m.getMonto());
                }
            }
            BigDecimal saldoAlInicio = saldo;

            // 2) Tomar movimientos dentro del rango y construir el detalle con saldos
            List<Movimiento> movsEnRango = movRepo.findByCuentaIdAndFechaCreacionBetweenOrderByFechaCreacionAsc(c.getId(), desde, hasta);
            for (Movimiento m : movsEnRango) {
                BigDecimal movimiento = m.getMonto();
                BigDecimal saldoInicialAntes = saldo; // antes de aplicar el movimiento
                // aplicar
                if (m.getTipo() == TipoMovimiento.DEPOSITO) {
                    saldo = saldo.add(movimiento);
                } else {
                    saldo = saldo.subtract(movimiento);
                }
                BigDecimal saldoDespues = saldo;

                items.add(new ReporteMovimientoItem(
                        m.getFechaCreacion(),
                        clienteNombre,
                        c.getNumero(),
                        c.getTipo(),
                        saldoInicialAntes,
                        c.getActiva(),
                        // Para visualizar retiros como negativos según ejemplo, usamos signo acorde al tipo
                        m.getTipo() == TipoMovimiento.RETIRO ? movimiento.negate() : movimiento,
                        saldoDespues
                ));
            }

            // Si no hubo movimientos en el rango, podríamos incluir una línea resumen con solo saldos si se requiere.
            if (movsEnRango.isEmpty()) {
                items.add(new ReporteMovimientoItem(
                        desde, // fecha de inicio del rango como referencia
                        clienteNombre,
                        c.getNumero(),
                        c.getTipo(),
                        saldoAlInicio,
                        c.getActiva(),
                        BigDecimal.ZERO,
                        saldoAlInicio
                ));
            }
        }

        // Mantener un orden global por fecha
        items.sort(Comparator.comparing(ReporteMovimientoItem::getFecha));
        return items;
    }
}
