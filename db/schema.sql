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
    password VARCHAR(255) NOT NULL,
    fecha_registro DATE NOT NULL,
    es_admin BOOLEAN DEFAULT FALSE
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
    categoria_id INT NOT NULL,
    nombre VARCHAR(150) NOT NULL,
    descripcion TEXT,
    precio DECIMAL(10,2) NOT NULL,
    origen VARCHAR(100),
    imagen_url VARCHAR(255),
    stock_disponible BOOLEAN DEFAULT TRUE,
    FOREIGN KEY (categoria_id) REFERENCES Categorias(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Tabla: Pedidos
-- Registra las compras realizadas por los usuarios
CREATE TABLE IF NOT EXISTS Pedidos (
    id INT PRIMARY KEY AUTO_INCREMENT,
    usuario_id INT NOT NULL,
    fecha_pedido DATETIME NOT NULL,
    total DECIMAL(10,2) NOT NULL,
    estado VARCHAR(50) NOT NULL,
    FOREIGN KEY (usuario_id) REFERENCES Usuarios(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ==========================================
-- Datos de Ejemplo (Inserts mínimos)
-- ==========================================

-- Usuario administrador
INSERT INTO Usuarios (nombre, email, password, fecha_registro, es_admin) VALUES
('Admin', 'admin@origenycódigo.com', 'admin123', '2024-01-01', TRUE);

-- Categorías de café
INSERT INTO Categorias (nombre, descripcion) VALUES
('Café en Grano', 'Café molido de diferentes orígenes y tostados'),
('Café de Especialidad', 'Café de alta calidad con certificación de origen');

-- Productos de ejemplo
INSERT INTO Productos (categoria_id, nombre, descripcion, precio, origen, imagen_url, stock_disponible) VALUES
(1, 'Café Colombianito', 'Café suave de Colombia con notas acidas', 12.99, 'Colombia', '/img/colombianito.jpg', TRUE),
(1, 'Café Etíope Yirgacheffe', 'Café floral y frutal de Etiopía', 18.50, 'Etiopía', '/img/etiopia.jpg', TRUE),
(2, 'Café Geisha Panama', 'Café premium de Panamá con notas cítricas', 45.00, 'Panamá', '/img/geisha.jpg', TRUE);