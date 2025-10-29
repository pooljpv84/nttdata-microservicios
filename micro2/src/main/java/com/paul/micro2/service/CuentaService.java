package com.paul.micro2.service;

import com.paul.micro2.domain.Cuenta;
import com.paul.micro2.dto.CuentaCreateRequest;
import com.paul.micro2.dto.CuentaResponse;
import com.paul.micro2.exception.BusinessException;
import com.paul.micro2.exception.NotFoundException;
import com.paul.micro2.repository.ClienteViewRepository;
import com.paul.micro2.repository.CuentaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class CuentaService {
    private final CuentaRepository repo;
    private final ClienteViewRepository clienteRepo;

    public Long create(CuentaCreateRequest r) {
        if (repo.existsByNumero(r.numero()))
            throw new BusinessException("El número de cuenta ya existe");

        var clienteIdExterno = r.clienteId() == null ? null : r.clienteId().trim();
        if (clienteIdExterno == null || clienteIdExterno.isEmpty()) {
            throw new BusinessException("clienteId inválido: requerido");
        }

        var cv = clienteRepo.findByClienteId(clienteIdExterno)
                .orElseThrow(() -> new NotFoundException("Cliente no existe (sincroniza eventos)"));

        if (!Boolean.TRUE.equals(cv.getEstado()))
            throw new BusinessException("Cliente inactivo");

        Long clientePk = cv.getId(); // PK real del cliente

        var c = Cuenta.builder()
                .numero(r.numero())
                .tipo(r.tipo())
                .clienteId(clientePk)          // guardamos la PK
                .clienteIdCod(clienteIdExterno) // y también el código externo
                .activa(true)
                .saldo(r.saldo())
                .build();
        return repo.save(c).getId();
    }

    @Transactional(readOnly = true)
    public CuentaResponse get(Long id) {
        var c = repo.findById(id).orElseThrow(() -> new NotFoundException("Cuenta no encontrada"));
        var nombre = clienteRepo.findById(c.getClienteId()).map(cv -> cv.getNombre()).orElse(null);
        return new CuentaResponse(
                c.getId(), c.getNumero(), c.getTipo(), c.getActiva(), c.getSaldo(),
                c.getClienteId(), c.getClienteIdCod(), nombre
        );
    }

    @Transactional(readOnly = true)
    public List<CuentaResponse> listarPorCliente(Long clienteId) {
        // Pre-cargar el nombre del cliente para evitar consulta por cada cuenta
        var nombre = clienteRepo.findById(clienteId).map(cv -> cv.getNombre()).orElse(null);
        return repo.findByClienteId(clienteId).stream()
                .map(c -> new CuentaResponse(
                        c.getId(),
                        c.getNumero(),
                        c.getTipo(),
                        c.getActiva(),
                        c.getSaldo(),
                        c.getClienteId(),
                        c.getClienteIdCod(),
                        nombre
                ))
                .toList();
    }

    @Transactional(readOnly = true)
    public List<CuentaResponse> listarTodas() {
        var cuentas = repo.findAll();
        Set<Long> clienteIds = cuentas.stream().map(Cuenta::getClienteId).collect(Collectors.toSet());
        Map<Long, String> nombresPorId = clienteRepo.findAllById(clienteIds).stream()
                .collect(Collectors.toMap(cv -> cv.getId(), cv -> cv.getNombre()));
        return cuentas.stream()
                .map(c -> new CuentaResponse(
                        c.getId(),
                        c.getNumero(),
                        c.getTipo(),
                        c.getActiva(),
                        c.getSaldo(),
                        c.getClienteId(),
                        c.getClienteIdCod(),
                        nombresPorId.get(c.getClienteId())
                ))
                .toList();
    }

    public void cambiarEstado(Long id, boolean activa) {
        var c = repo.findById(id).orElseThrow(() -> new NotFoundException("Cuenta no encontrada"));
        c.setActiva(activa);
        repo.save(c);
    }
}
