// micro1 - controller/ClienteAdminController.java
package com.paul.micro1.controller;

import com.paul.micro1.domain.Cliente;
import com.paul.micro1.events.ClienteEventProducer;
import com.paul.micro1.repository.ClienteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/clientes/admin")
@RequiredArgsConstructor
public class ClienteAdminController {
    private final ClienteRepository repo;
    private final ClienteEventProducer producer;

    // POST /api/clientes/admin/publishAll
    @PostMapping("/publishAll")
    public Map<String, Object> publishAll() {
        var all = repo.findAll();
        int count = 0;
        for (Cliente c : all) {
            producer.publishCreated(c); // re-emite ClienteCreated para cada uno
            count++;
        }
        return Map.of("status","OK","message","Eventos publicados","count",count);
    }
}
