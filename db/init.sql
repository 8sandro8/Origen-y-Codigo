-- ==========================================
-- Script de Inicialización - Origen & Código
-- Base de datos: origen_codigo
-- ==========================================

USE origen_codigo;

-- ==========================================
-- Categorías (si no existen)
-- ==========================================
INSERT INTO categorias (nombre, descripcion) VALUES 
('Café en Grano', 'Café molido de diferentes orígenes y tostados'),
('Café de Especialidad', 'Café de alta calidad con certificación de origen')
ON DUPLICATE KEY UPDATE nombre = nombre;

-- ==========================================
-- Productos - 6 Cafés de Especialidad
-- ==========================================
INSERT INTO productos (categoria_id, nombre, descripcion, precio, origen, imagen_url, stock_disponible) VALUES
(2, 'Geisha Panama', 'Café premium de Panamá con notas cítricas y florales.', 42.00, 'Panamá', NULL, 1),
(2, 'Yirgacheffe Etiopía', 'Café de alta altitud con notas frutales y aroma a jazmín.', 28.50, 'Etiopía', NULL, 1),
(2, 'Sidra Honduras', 'Café de proceso anaeróbico con notas de chocolate y frutas rojas.', 24.00, 'Honduras', NULL, 1),
(2, 'Blue Mountain Jamaica', 'Café clásico de Jamaica con equilibrio perfecto.', 38.00, 'Jamaica', NULL, 1),
(2, 'Tarrazú Costa Rica', 'Café de altura con cuerpo completo y acidez brillante.', 26.00, 'Costa Rica', NULL, 1),
(2, 'Mandheling Sumatra', 'Café indonesio de baja acidez con notas terrosas.', 22.00, 'Sumatra', NULL, 1)
ON DUPLICATE KEY UPDATE nombre = nombre;
