package com.paul.micro1.controller;

import com.paul.micro1.dto.ClienteRequest;
import com.paul.micro1.dto.ClienteResponse;
import com.paul.micro1.dto.ClienteUpdateRequest;
import com.paul.micro1.dto.ClientePersonaCreateRequest;
import com.paul.micro1.dto.PersonaResponse;
import com.paul.micro1.exception.ApiResponse;
import com.paul.micro1.service.ClienteService;
import com.paul.micro1.service.PersonaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/clientes")
@RequiredArgsConstructor
public class ClienteController {

    private final ClienteService clienteService;
    private final PersonaService personaService;

    // POST /api/clientes/post
    @PostMapping("/post")
    public Map<String, Object> create(@RequestBody @Valid ClienteRequest req) {
        Long id = clienteService.create(req);
        ClienteResponse cliente = clienteService.getByPk(id);
        return ApiResponse.okWith("Cliente registrado correctamente", "cliente", cliente);
    }

    // Listado en "/"
    @GetMapping("/getAll")
    public Map<String, Object> listar() {
        List<ClienteResponse> items = clienteService.listar();
        return ApiResponse.okWith("Listado de clientes obtenido correctamente", Map.of("items", items));
    }

    // GET /api/clientes/getByClientePk/{id}
    @GetMapping("/getByClientePk/{id:\\d+}")
    public Map<String, Object> getByPk(@PathVariable Long id) {
        ClienteResponse cliente = clienteService.getByPk(id);
        return ApiResponse.okWith("Cliente obtenido correctamente", "cliente", cliente);
    }

    // Alias: GET /api/clientes/getByPersonaPk/{id}
    @GetMapping("/getByPersonaPk/{id:\\d+}")
    public Map<String, Object> getByPersonaPk(@PathVariable Long id) {
        ClienteResponse cliente = clienteService.getByPk(id);
        return ApiResponse.okWith("Cliente obtenido correctamente", "cliente", cliente);
    }

    // PUT /api/clientes/put/{id}
    @PutMapping("/put/{id:\\d+}")
    public Map<String, Object> update(@PathVariable Long id, @RequestBody @Valid ClienteUpdateRequest req) {
        clienteService.update(id, req);
        ClienteResponse cliente = clienteService.getByPk(id);
        PersonaResponse persona = personaService.getPersonaById(id);
        return ApiResponse.okWith("Cliente y persona actualizados correctamente", Map.of(
                "cliente", cliente,
                "persona", persona
        ));
    }

    // DELETE /api/clientes/delete/{id}
    @DeleteMapping("/delete/{id:\\d+}")
    public Map<String, String> delete(@PathVariable Long id) {
        clienteService.delete(id);
        return ApiResponse.ok("Cliente borrado exitosamente");
    }

    // GET por clienteId (string)
    @GetMapping("/getByClienteId/{clienteId}")
    public Map<String, Object> getByClienteId(@PathVariable String clienteId) {
        ClienteResponse cliente = clienteService.getClienteById(clienteId);
        return ApiResponse.okWith("Cliente obtenido correctamente", "cliente", cliente);
    }

    // GET por identificacion
    @GetMapping("/getByIdentificacion/{identificacion}")
    public Map<String, Object> getByIdentificacion(@PathVariable String identificacion) {
        ClienteResponse cliente = clienteService.getByIdentificacion(identificacion);
        return ApiResponse.okWith("Cliente obtenido correctamente", "cliente", cliente);
    }

    // Cambiar estado por clienteId (string)
    @PatchMapping("/patchEstado/{clienteId}")
    public Map<String, String> cambiarEstado(@PathVariable String clienteId, @RequestParam boolean activo) {
        clienteService.cambiarEstado(clienteId, activo);
        return ApiResponse.ok("Estado de cliente actualizado correctamente");
    }
}
