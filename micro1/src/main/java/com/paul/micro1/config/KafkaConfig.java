package com.paul.micro1.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KafkaConfig {
    @Bean
    public NewTopic clientesEvents() {
        return new NewTopic("clientes.events", 1, (short) 1);
    }
}
