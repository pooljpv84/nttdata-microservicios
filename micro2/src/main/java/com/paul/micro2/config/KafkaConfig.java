package com.paul.micro2.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KafkaConfig {
    // Asegura que exista el tópico de clientes (por si el broker no crea auto)
    // apuntar al topic
    @Bean
    public NewTopic clientesEventsTopic() {
        return new NewTopic("clientes.events", 1, (short) 1);
    }
}
