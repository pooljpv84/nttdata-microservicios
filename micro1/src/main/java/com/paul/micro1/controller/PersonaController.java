package com.paul.micro1.controller;

import com.paul.micro1.dto.PersonaRequest;
import com.paul.micro1.dto.PersonaResponse;
import com.paul.micro1.exception.ApiResponse;
import com.paul.micro1.service.PersonaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/personas")
@RequiredArgsConstructor
public class PersonaController {

    private final PersonaService service;

    // POST /api/personas/post
    @PostMapping({"/post"})
    public Map<String, Object> create(@RequestBody @Valid PersonaRequest req) {
        Long id = service.create(req);
        PersonaResponse persona = service.getPersonaById(id);
        return ApiResponse.okWith("Persona registrada correctamente", "persona", persona);
    }


    // PUT /api/personas/put/{id}
    @PutMapping("/put/{id}")
    public Map<String, Object> update(@PathVariable Long id, @RequestBody @Valid PersonaRequest req) {
        service.update(id, req);
        PersonaResponse persona = service.getPersonaById(id);
        return ApiResponse.okWith("Persona actualizada correctamente", "persona", persona);
    }

    // GET /api/personas/getPersona/{id}
    @GetMapping("/getPersona/{id}")
    public Map<String, Object> getPersonaByIdAlias(@PathVariable Long id) {
        PersonaResponse persona = service.getPersonaById(id);
        return ApiResponse.okWith("Persona obtenida correctamente", "persona", persona);
    }

    // Listado en "/"
    @GetMapping("/getAll")
    public Map<String, Object> listar(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sort) {
        Page<PersonaResponse> result = service.listar(page, size, sort);
        return ApiResponse.okWith("Listado de personas obtenido correctamente", Map.of("page", result));
    }

    // DELETE /api/personas/delete/{id}
    @DeleteMapping("/delete/{id}")
    public Map<String, String> delete(@PathVariable Long id) {
        service.delete(id);
        return ApiResponse.ok("Persona borrada exitosamente");
    }

}
