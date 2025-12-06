USE transportdb;

-- Desactivar temporalmente las comprobaciones de claves foráneas
SET FOREIGN_KEY_CHECKS = 0;

-- Limpiar tablas en orden correcto
TRUNCATE TABLE factura;
TRUNCATE TABLE conductor_has_vehiculo;
TRUNCATE TABLE pedido_has_ruta;
TRUNCATE TABLE pedido;
TRUNCATE TABLE producto;
TRUNCATE TABLE vehiculo;
TRUNCATE TABLE transporte;
TRUNCATE TABLE conductor;
TRUNCATE TABLE ruta;
TRUNCATE TABLE cliente;
TRUNCATE TABLE almacen;

-- Reactivar comprobaciones de claves foráneas
SET FOREIGN_KEY_CHECKS = 1;

-- ----------------------------
-- Datos para tabla almacen 
-- ----------------------------
INSERT INTO almacen (capacidad_maxima, nombre, ubicacion) VALUES
(100, 'Almacen Central', 'Madrid'),
(50, 'Almacen Norte', 'Barcelona'),
(75, 'Almacen Sur', 'Sevilla');

-- ----------------------------
-- Datos para tabla cliente
-- ----------------------------
INSERT INTO cliente (direccion, dni, email, name, telefono) VALUES
('Calle Mayor 1, Madrid', '12345678A', 'juan@mail.com', 'Juan Perez', 600123456),
('Calle Luna 22, Sevilla', '87654321B', 'maria@mail.com', 'Maria Lopez', 600654321);

-- ----------------------------
-- Datos para tabla ruta
-- ----------------------------
INSERT INTO ruta (id_ruta, origen, destino, distancia, tiempo_estimado) VALUES
(1, 'Madrid', 'Barcelona', 620, 6.5),
(2, 'Sevilla', 'Valencia', 660, 7.0),
(3, 'Barcelona', 'Sevilla', 1000, 10.0);

-- ----------------------------
-- Datos para tabla pedido
-- ----------------------------
INSERT INTO pedido (estado_pedido, fecha_entrega, fecha_pedido, id_cliente) VALUES
('En camino', '2025-11-10', '2025-11-05', 1),
('Pendiente', '2025-11-12', '2025-11-05', 2);

-- ----------------------------
-- Datos para tabla pedido_has_ruta
-- ----------------------------
INSERT INTO pedido_has_ruta (id_pedido, id_ruta) VALUES
(1, 1),
(1, 3),
(2, 2);

-- ----------------------------
-- Datos para tabla producto
-- ----------------------------
INSERT INTO producto (descripcion, nombre, precio_unitario, stock, id_almacen) VALUES
('Paquete de 10 cajas', 'Caja de bebidas', 50.0, 200, 1),
('Palet de cereales', 'Cereal', 120.0, 50, 2),
('Bolsa de arroz', 'Arroz', 30.0, 100, 3);

-- ----------------------------
-- Datos para tabla transporte
-- ----------------------------
INSERT INTO transporte (capacidad, estado_transporte, tipo_transporte) VALUES
(1000, 'Disponible', 'Camión'),
(500, 'En reparación', 'Furgoneta');

-- ----------------------------
-- Datos para tabla vehiculo
-- ----------------------------
INSERT INTO vehiculo (anyo, capacidad, matricula, modelo, id_transporte) VALUES
(2020, 1000, '1234ABC', 'Volvo FH', 1),
(2018, 500, '5678DEF', 'Mercedes Sprinter', 2);

-- ----------------------------
-- Datos para tabla conductor
-- ----------------------------
INSERT INTO conductor (direccion, licencia, nombre, telefono) VALUES
('Calle Sol 10, Madrid', 'B1234567', 'Carlos Ruiz', 600111222),
('Avenida Rio 5, Sevilla', 'C7654321', 'Lucia Martinez', 600333444);

-- ----------------------------
-- Datos para tabla conductor_has_vehiculo
-- ----------------------------
INSERT INTO conductor_has_vehiculo (id_conductor, id_vehiculo) VALUES
(1, 1),
(2, 2);

-- ----------------------------
-- Datos para tabla factura
-- ----------------------------
INSERT INTO factura (id_factura, fecha_emision, metodo_pago, subtotal, id_pedido) VALUES
(1, '2025-11-05', 'Tarjeta', 500.0, 1),
(2, '2025-11-05', 'Efectivo', 300.0, 2);
