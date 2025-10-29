package com.paul.micro2.exception;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Utilidad para construir respuestas estándar de la API.
 * Formato: {"message": "...", "status": "OK|ERROR"}
 */
public final class ApiResponse {
    private ApiResponse() {}

    public static final String STATUS_OK = "OK";
    public static final String STATUS_ERROR = "ERROR";

    // Respuesta simple OK
    public static Map<String, String> ok(String message) {
        return Map.of("message", message, "status", STATUS_OK);
    }

    // Respuesta simple ERROR
    public static Map<String, String> error(String message) {
        return Map.of("message", message, "status", STATUS_ERROR);
    }

    // OK con un dato adicional (por ejemplo, persona actualizada, objeto creado, etc.)
    public static Map<String, Object> okWith(String message, String key, Object value) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("status", STATUS_OK);
        body.put("message", message);
        body.put(key, value);
        return body;
    }

    // OK con varios datos adicionales
    public static Map<String, Object> okWith(String message, Map<String, Object> extras) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("status", STATUS_OK);
        body.put("message", message);
        if (extras != null) body.putAll(extras);
        return body;
    }
}
