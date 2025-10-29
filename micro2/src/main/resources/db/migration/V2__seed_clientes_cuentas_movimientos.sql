INSERT INTO cliente_view (id, cliente_id, identificacion, nombre, estado)
VALUES (1, '001', '1111111111', 'Jose Lema', 1),
       (2, '002', '2222222222', 'Marianela Montalvo', 1),
       (3, '003', '3333333333', 'Juan Osorio', 1);
INSERT INTO cuenta (id, cliente_id, cliente_id_cod, numero, tipo, activa, saldo)
VALUES (1, 1, '001', '478758', 'Ahorros', 1, 850.00),
       (2, 2, '002', '225487', 'Corriente', 1, 1300.00),
       (3, 3, '003', '495878', 'Ahorros', 1, 150.00),
       (4, 2, '002', '496825', 'Ahorros', 1, 0.00),
       (5, 1, '001', '585545', 'Corriente', 1, 1000.00);
INSERT INTO movimiento (id, cuenta_id, tipo, monto, referencia, fecha_creacion)
VALUES (3, 1, 'RETIRO', 575.00, 'RETIRO de 575', '2025-10-28 17:10:01'),
       (4, 2, 'DEPOSITO', 600.00, 'DEPOSITO de 600', '2025-10-28 17:10:06'),
       (5, 3, 'DEPOSITO', 150.00, 'DEPOSITO de 150', '2025-10-28 17:10:11'),
       (6, 4, 'RETIRO', 540.00, 'RETIRO de 540', '2025-10-28 17:10:16');