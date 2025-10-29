-- Crear base de datos
CREATE DATABASE IF NOT EXISTS persona_db CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;
USE persona_db;

-- Tabla: persona
CREATE TABLE IF NOT EXISTS persona (
    id              BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre          VARCHAR(100)  NOT NULL,
    genero          VARCHAR(20),
    edad            INT,
    identificacion  VARCHAR(20)   NOT NULL UNIQUE,
    direccion       VARCHAR(255),
    telefono        VARCHAR(20)
);

-- Tabla: cliente (hereda de persona)
CREATE TABLE IF NOT EXISTS cliente (
    id             BIGINT      PRIMARY KEY,                -- mismo id que persona
    clienteid      VARCHAR(50) NOT NULL UNIQUE,            -- identificador lógico
    contrasenia    VARCHAR(255) NOT NULL,
    estado         TINYINT(1)  NOT NULL DEFAULT 1,
    CONSTRAINT fk_cliente_persona
        FOREIGN KEY (id) REFERENCES persona(id)
        ON UPDATE CASCADE 
        ON DELETE CASCADE
);
