-- ==========================================
-- Schema SQL - Origen & Código (Tienda Café)
-- Motor: MariaDB
-- ==========================================

-- Tabla: Usuarios
-- Almacena los datos de los usuarios registrados en la plataforma
CREATE TABLE IF NOT EXISTS Usuarios (
    id INT PRIMARY KEY AUTO_INCREMENT,
    nombre VARCHAR(100) NOT NULL,
    email VARCHAR(150) NOT NULL UNIQUE,
    contrasena VARCHAR(255) NOT NULL,
    fechaRegistro DATE NOT NULL,
    esAdmin BOOLEAN DEFAULT FALSE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Tabla: Categorías
-- Clasifica los productos de café por tipo
CREATE TABLE IF NOT EXISTS Categorias (
    id INT PRIMARY KEY AUTO_INCREMENT,
    nombre VARCHAR(100) NOT NULL,
    descripcion TEXT
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Tabla: Productos
-- Catálogo de cafés disponibles en la tienda
CREATE TABLE IF NOT EXISTS Productos (
    id INT PRIMARY KEY AUTO_INCREMENT,
    categoriaId INT NOT NULL,
    nombre VARCHAR(150) NOT NULL,
    descripcion TEXT,
    precio DECIMAL(10,2) NOT NULL,
    origen VARCHAR(100),
    imagenUrl VARCHAR(255),
    stockDisponible BOOLEAN DEFAULT TRUE,
    FOREIGN KEY (categoriaId) REFERENCES Categorias(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Tabla: Pedidos
-- Registra las compras realizadas por los usuarios
CREATE TABLE IF NOT EXISTS Pedidos (
    id INT PRIMARY KEY AUTO_INCREMENT,
    usuarioId INT NOT NULL,
    fechaPedido DATETIME NOT NULL,
    total DECIMAL(10,2) NOT NULL,
    estado VARCHAR(50) NOT NULL,
    FOREIGN KEY (usuarioId) REFERENCES Usuarios(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ==========================================
-- Datos de Ejemplo (Inserts mínimos)
-- ==========================================

-- Usuario administrador
INSERT INTO Usuarios (nombre, email, contrasena, fechaRegistro, esAdmin) VALUES
('Admin', 'admin@origenycódigo.com', '240be518fabd2724ddb6f04eeb1da5967448d7e831c08c8fa822809f74c720a9', '2024-01-01', TRUE);

-- Categorías de café
INSERT INTO Categorias (nombre, descripcion) VALUES
('Café en Grano', 'Café molido de diferentes orígenes y tostados'),
('Café de Especialidad', 'Café de alta calidad con certificación de origen');

-- Productos de ejemplo
INSERT INTO Productos (categoriaId, nombre, descripcion, precio, origen, imagenUrl, stockDisponible) VALUES
(1, 'Café Colombianito', 'Café suave de Colombia con notas acidas', 12.99, 'Colombia', '/img/colombianito.jpg', TRUE),
(1, 'Café Etíope Yirgacheffe', 'Café floral y frutal de Etiopía', 18.50, 'Etiopía', '/img/etiopia.jpg', TRUE),
(2, 'Café Geisha Panama', 'Café premium de Panamá con notas cítricas', 45.00, 'Panamá', '/img/geisha.jpg', TRUE);