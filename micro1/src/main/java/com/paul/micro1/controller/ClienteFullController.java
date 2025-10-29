package com.paul.micro1.controller;

import com.paul.micro1.dto.*;
import com.paul.micro1.exception.ApiResponse;
import com.paul.micro1.service.ClienteService;
import com.paul.micro1.service.PersonaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/clientes")
@RequiredArgsConstructor
public class ClienteFullController {
    private final ClienteService clienteService;
    private final PersonaService personaService;

    // POST combinado -- crea Persona y Cliente en un solo endpoint
    @PostMapping("/postFull")
    public Map<String, Object> createFull(@RequestBody @Valid ClientePersonaCreateRequest req) {
        Long id = clienteService.createFull(req);
        ClienteResponse cliente = clienteService.getByPk(id);
        PersonaResponse persona = personaService.getPersonaById(id);
        return ApiResponse.okWith("Cliente y persona registrados correctamente", Map.of(
                "cliente", cliente,
                "persona", persona
        ));
    }

    // GET combinado - lista todos cliente+persona
    @GetMapping("/getAllClientePersona")
    public Map<String, Object> getAllClientePersona() {
        List<ClienteResponse> clientes = clienteService.listar();
        List<ClientePersonaResponse> items = clientes.stream()
                .map(c -> new ClientePersonaResponse(c, personaService.getPersonaById(c.id())))
                .collect(Collectors.toList());
        return ApiResponse.okWith("Listado Full", Map.of("items", items));
    }

    // GET combinado por ID de persona/cliente
    @GetMapping("/getClientePersona/{id:\\d+}")
    public Map<String, Object> getClientePersona(@PathVariable Long id) {
        ClienteResponse cliente = clienteService.getByPk(id);
        PersonaResponse persona = personaService.getPersonaById(id);
        return ApiResponse.okWith("Cliente y persona obtenidos correctamente", Map.of(
                "cliente", cliente,
                "persona", persona
        ));
    }

    // DELETE - solo borra el cliente sin eliminar la persona
    @DeleteMapping("/deleteClienteOnly/{id:\\d+}")
    public Map<String, String> deleteClienteOnly(@PathVariable Long id) {
        clienteService.delete(id);
        return ApiResponse.ok("Cliente borrado exitosamente (persona preservada)");
    }

    // PUT combinado - actualiza datos de persona y cliente
    @PutMapping("/putClientePersona/{id:\\d+}")
    public Map<String, Object> updateClientePersona(@PathVariable Long id,
                                                    @RequestBody @Valid ClientePersonaUpdateRequest req) {
        // mantener la identificacion original para PersonaRequest
        PersonaResponse actual = personaService.getPersonaById(id);
        PersonaRequest personaReq = new PersonaRequest(
                req.nombre(),
                req.genero(),
                req.edad(),
                actual.identificacion(), // no cambia
                req.direccion(),
                req.telefono()
        );
        personaService.update(id, personaReq);

        ClienteUpdateRequest clienteReq = new ClienteUpdateRequest(req.contrasenia(), req.estado());
        clienteService.update(id, clienteReq);

        ClienteResponse cliente = clienteService.getByPk(id);
        PersonaResponse persona = personaService.getPersonaById(id);
        return ApiResponse.okWith("Cliente y persona actualizados correctamente", Map.of(
                "cliente", cliente,
                "persona", persona
        ));
    }
}
