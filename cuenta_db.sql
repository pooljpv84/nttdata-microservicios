-- Crear base de datos
CREATE DATABASE IF NOT EXISTS cuenta_db CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;
USE cuenta_db;

-- Tabla: cliente_view (sin FK real, replica desde micro1)
CREATE TABLE IF NOT EXISTS cliente_view (
    id              BIGINT       NOT NULL,          -- misma PK de persona_db.cliente
    cliente_id      VARCHAR(50)  NOT NULL,
    identificacion  VARCHAR(20)  NOT NULL,
    nombre          VARCHAR(100) NOT NULL,
    estado          TINYINT(1)   NOT NULL DEFAULT 1,
    PRIMARY KEY (id),
    UNIQUE KEY uq_cliente_view_clienteid (cliente_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Tabla: cuenta
CREATE TABLE IF NOT EXISTS cuenta (
    id               BIGINT        NOT NULL AUTO_INCREMENT,
    cliente_id       BIGINT        NOT NULL,           -- referencia lógica al cliente_view.id
    cliente_id_cod   VARCHAR(50)   NOT NULL,           -- código externo del cliente
    numero           VARCHAR(30)   NOT NULL,
    tipo             VARCHAR(20)   NOT NULL,           -- AHO / CORR /
    activa           TINYINT(1)    NOT NULL DEFAULT 1,
    saldo            DECIMAL(18,2) NOT NULL DEFAULT 0.00,
    PRIMARY KEY (id),
    UNIQUE KEY uq_cuenta_numero (numero),
    KEY ix_cuenta_cliente (cliente_id),
    KEY ix_cuenta_cliente_id_cod (cliente_id_cod)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Tabla: movimiento
CREATE TABLE IF NOT EXISTS movimiento (
    id              BIGINT        NOT NULL AUTO_INCREMENT,
    cuenta_id       BIGINT        NOT NULL,
    tipo            VARCHAR(12)   NOT NULL,          -- DEPOSITO | RETIRO
    monto           DECIMAL(18,2) NOT NULL,
    referencia      VARCHAR(100)  NULL,
    fecha_creacion  DATETIME      NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (id),
    KEY ix_mov_cuenta_fecha (cuenta_id, fecha_creacion),
    CONSTRAINT fk_mov_cuenta FOREIGN KEY (cuenta_id) REFERENCES cuenta(id)
        ON UPDATE RESTRICT 
        ON DELETE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

