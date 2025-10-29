package com.paul.micro1.events;

import java.time.Instant;

public record ClienteEvent(
        String eventType,
        Instant occurredOn,
        ClienteEventData data
) {}

