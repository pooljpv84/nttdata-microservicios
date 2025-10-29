package com.paul.micro2.service;

import com.paul.micro2.domain.Cuenta;
import com.paul.micro2.domain.Movimiento;
import com.paul.micro2.domain.TipoMovimiento;
import com.paul.micro2.dto.MovimientoCreateRequest;
import com.paul.micro2.dto.MovimientoCreateResult;
import com.paul.micro2.dto.MovimientoResponse;
import com.paul.micro2.exception.BusinessException;
import com.paul.micro2.exception.NotFoundException;
import com.paul.micro2.repository.CuentaRepository;
import com.paul.micro2.repository.MovimientoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class MovimientoService {
    private final MovimientoRepository movRepo;
    private final CuentaRepository cuentaRepo;

    public MovimientoCreateResult registrar(MovimientoCreateRequest r) {
        Cuenta cuenta = cuentaRepo.findByNumero(r.numeroCuenta())
                .orElseThrow(() -> new NotFoundException("Cuenta no encontrada"));
        if (!Boolean.TRUE.equals(cuenta.getActiva()))
            throw new BusinessException("La cuenta está inactiva");

        BigDecimal saldoInicial = cuenta.getSaldo();
        BigDecimal saldo = saldoInicial;
        if (r.tipoMovimiento() == TipoMovimiento.DEPOSITO) {
            saldo = saldo.add(r.valor());
        } else {
            if (saldo.compareTo(r.valor()) < 0)
                throw new BusinessException("Saldo no disponible");
            saldo = saldo.subtract(r.valor());
        }
        cuenta.setSaldo(saldo);
        cuentaRepo.save(cuenta);

        String referencia = r.tipoMovimiento().name() + " de " + r.valor().stripTrailingZeros().toPlainString();

        Movimiento m = Movimiento.builder()
                .cuentaId(cuenta.getId())
                .tipo(r.tipoMovimiento())
                .monto(r.valor())
                .referencia(referencia)
                .build();
        m = movRepo.save(m);

        MovimientoResponse mResp = new MovimientoResponse(m.getId(), m.getCuentaId(), m.getTipo(), m.getMonto(), m.getReferencia(), m.getFechaCreacion());
        return new MovimientoCreateResult(
                cuenta.getNumero(),
                cuenta.getTipo(),
                saldoInicial,
                saldo,
                cuenta.getActiva(),
                mResp
        );
    }

    @Transactional(readOnly = true)
    public List<MovimientoResponse> listar(Long cuentaId) {
        return movRepo.findByCuentaIdOrderByFechaCreacionDesc(cuentaId).stream()
                .map(m -> new MovimientoResponse(m.getId(), m.getCuentaId(), m.getTipo(), m.getMonto(), m.getReferencia(), m.getFechaCreacion()))
                .toList();
    }
}
