package com.paul.micro2.service;

import com.paul.micro2.domain.ClienteView;
import com.paul.micro2.repository.ClienteViewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

/**
 * Escucha los eventos de clientes emitidos por micro1
 * y actualiza la tabla cliente_view en micro2.
 */
@Component
@RequiredArgsConstructor
public class ClienteEventsConsumer {

    private final ClienteViewRepository repo;

    // Records internos para mapear el JSON del evento
    public record ClienteEvent(String eventType, Instant occurredOn, ClienteEventData data) {
    }

    public record ClienteEventData(Long id, String clienteId, String identificacion, String nombre, Boolean estado) {
    }

    @KafkaListener(topics = "clientes.events", groupId = "micro2-cuentas")
    @Transactional
    public void onMessage(ClienteEvent evt) {
        if (evt == null || evt.data() == null) return;

        var tipo = evt.eventType();
        var d = evt.data();

        if ("ClienteCreated".equals(tipo) ||
                "ClienteUpdated".equals(tipo) ||
                "ClienteEstadoChanged".equals(tipo)) {

            var v = ClienteView.builder()
                    .id(d.id())
                    .clienteId(d.clienteId())
                    .identificacion(d.identificacion())
                    .nombre(d.nombre())
                    .estado(Boolean.TRUE.equals(d.estado()))
                    .build();

            repo.save(v); // upsert
            System.out.printf("✅ Evento %s procesado para cliente %s%n", tipo, d.clienteId());

        } else if ("ClienteDeleted".equals(tipo)) {
            repo.findById(d.id()).ifPresent(c -> {
                repo.deleteById(d.id());
                System.out.printf("🗑️ Cliente eliminado: %s%n", d.clienteId());
            });
        } else {
            System.out.printf("⚠️ Evento desconocido: %s%n", tipo);
        }
    }
}
