package com.paul.micro2.controller;

import com.paul.micro2.dto.*;
import com.paul.micro2.exception.ApiResponse;
import com.paul.micro2.service.CuentaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/cuentas")
@RequiredArgsConstructor
public class CuentaController {
    private final CuentaService service;

    @PostMapping("/post")
    public Map<String,Object> create(@RequestBody @Valid CuentaCreateRequest r){
        Long id = service.create(r);
        return ApiResponse.okWith("Cuenta creada", "cuenta", service.get(id));
    }

    @GetMapping("/get/{id}")
    public Map<String,Object> get(@PathVariable Long id){
        return ApiResponse.okWith("Cuenta obtenida", "cuenta", service.get(id));
    }

    @GetMapping("/getAllCuentas")
    public Map<String,Object> getAllCuentas(){
        return ApiResponse.okWith("Listado de cuentas", Map.of("items", service.listarTodas()));
    }

    @GetMapping("/getByCliente/{clienteId}")
    public Map<String,Object> getByCliente(@PathVariable Long clienteId){
        return ApiResponse.okWith("Listado de cuentas",
                Map.of("items", service.listarPorCliente(clienteId)));
    }

    @PatchMapping("/patchEstado/{id}")
    public Map<String,String> estado(@PathVariable Long id, @RequestParam boolean activa){
        service.cambiarEstado(id, activa);
        return ApiResponse.ok("Estado de cuenta actualizado");
    }
}
