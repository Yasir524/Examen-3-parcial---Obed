-- 1. Crear la base de datos (Criterio 2.1)
CREATE DATABASE IF NOT EXISTS SistemaPedidos;
USE SistemaPedidos;

-- 2. Crear la tabla principal (Criterio 2.2)
CREATE TABLE IF NOT EXISTS Pedido (
    id_pedido INT PRIMARY KEY,
    cliente VARCHAR(100) NOT NULL,
    producto VARCHAR(100) NOT NULL,
    cantidad INT NOT NULL,
    estado VARCHAR(20) DEFAULT 'PENDIENTE',
    hilo_proceso VARCHAR(50) DEFAULT NULL
);

-- 3. Datos de prueba 
INSERT INTO Pedido (id_pedido, cliente, producto, cantidad, estado) 
VALUES
    -> (1, 'Juan Perez', 'Laptop', 1, 'PENDIENTE'),
    -> (2, 'Maria Lopez', 'Mouse', 2, 'PENDIENTE'),
    -> (3, 'Carlos Ruiz', 'Monitor', 1, 'PENDIENTE'),
    -> (4, 'Ana Gomez', 'Teclado', 3, 'PENDIENTE');
