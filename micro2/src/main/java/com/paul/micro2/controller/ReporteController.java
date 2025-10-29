package com.paul.micro2.controller;

import com.paul.micro2.exception.ApiResponse;
import com.paul.micro2.service.ReporteService;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.Map;

@RestController
@RequestMapping("/api/reportes")
@RequiredArgsConstructor
public class ReporteController {
    private final ReporteService reporteService;

    @GetMapping
    public Map<String, Object> estadoCuenta(
            @RequestParam @NotNull Long clienteId,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate desde,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate hasta,
            @RequestParam(required = false, name = "fecha") String rangoFecha
    ) {
        // Admite: ?fecha=YYYY-MM-DD,YYYY-MM-DD o parámetros separados ?desde=...&hasta=...
        if (rangoFecha != null && !rangoFecha.isBlank()) {
            String[] parts = rangoFecha.split(",");
            if (parts.length == 2) {
                desde = LocalDate.parse(parts[0].trim());
                hasta = LocalDate.parse(parts[1].trim());
            } else if (parts.length == 1) {
                desde = LocalDate.parse(parts[0].trim());
                hasta = parts[0].trim().isEmpty() ? null : LocalDate.parse(parts[0].trim());
            }
        }

        var items = reporteService.estadoCuenta(clienteId, desde, hasta);
        return ApiResponse.okWith("Reporte de estado de cuenta", Map.of(
                "clienteId", clienteId,
                "desde", desde,
                "hasta", hasta,
                "items", items
        ));
    }
}
