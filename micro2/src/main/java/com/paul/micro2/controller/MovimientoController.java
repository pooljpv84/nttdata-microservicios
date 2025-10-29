package com.paul.micro2.controller;

import com.paul.micro2.dto.MovimientoCreateRequest;
import com.paul.micro2.dto.MovimientoCreateResult;
import com.paul.micro2.exception.ApiResponse;
import com.paul.micro2.service.MovimientoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedHashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/movimientos")
@RequiredArgsConstructor
public class MovimientoController {
    private final MovimientoService service;

    @PostMapping("/postMovimiento")
    public Map<String,Object> post(@RequestBody @Valid MovimientoCreateRequest r){
        MovimientoCreateResult res = service.registrar(r);

        Map<String, Object> extras = new LinkedHashMap<>(); // <--- orden
        extras.put("numeroCuenta", res.numeroCuenta());
        extras.put("tipoCuenta", res.tipoCuenta());
        extras.put("saldoInicial", res.saldoInicial());
        extras.put("saldoDisponible", res.saldoDisponible());
        extras.put("estadoCuenta", res.estadoCuenta());
        extras.put("movimiento", res.movimiento());

        return ApiResponse.okWith("Movimiento registrado", extras);
    }

    @GetMapping("/getByCuenta/{cuentaId}")
    public Map<String,Object> list(@PathVariable Long cuentaId){
        return ApiResponse.okWith("Listado movimientos",
                Map.of("items", service.listar(cuentaId)));
    }
}
