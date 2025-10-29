package com.paul.micro1.events;

import com.paul.micro1.domain.Cliente;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
@RequiredArgsConstructor
public class ClienteEventProducer {

    private final KafkaTemplate<String, Object> kafka; // JsonSerializer

    private static final String TOPIC = "clientes.events";

    private ClienteEventData data(Cliente c) {
        return new ClienteEventData(
                c.getId(),
                c.getClienteId(),
                c.getIdentificacion(),
                c.getNombre(),
                c.getEstado()
        );
    }

    public void publishCreated(Cliente c) {
        var evt = new ClienteEvent("ClienteCreated", Instant.now(), data(c));
        kafka.send(TOPIC, String.valueOf(c.getId()), evt);
    }

    public void publishUpdated(Cliente c) {
        var evt = new ClienteEvent("ClienteUpdated", Instant.now(), data(c));
        kafka.send(TOPIC, String.valueOf(c.getId()), evt);
    }

    public void publishEstadoChanged(Cliente c) {
        var evt = new ClienteEvent("ClienteEstadoChanged", Instant.now(), data(c));
        kafka.send(TOPIC, String.valueOf(c.getId()), evt);
    }

    public void publishDeleted(Long id) {
        var evt = new ClienteEvent("ClienteDeleted", Instant.now(),
                new ClienteEventData(id, null, null, null, null));
        kafka.send(TOPIC, String.valueOf(id), evt);
    }
}
