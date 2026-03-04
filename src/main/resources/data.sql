-- ================================
-- Tabla: rol
-- ================================
CREATE TABLE IF NOT EXISTS rol (
    id INT AUTO_INCREMENT PRIMARY KEY,
    descripcion VARCHAR(40) NOT NULL
);

-- ================================
-- Tabla: usuario
-- ================================
CREATE TABLE IF NOT EXISTS usuario (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    apellido VARCHAR(100) NOT NULL,
    documento VARCHAR(15) NOT NULL,
    email VARCHAR(100) NOT NULL,
    password VARCHAR(255),
    rol_id INT,
    UNIQUE (documento),
    CONSTRAINT fk_usuario_rol FOREIGN KEY (rol_id) REFERENCES rol(id)
);


-- ================================
-- Datos iniciales: rol
-- ================================
INSERT IGNORE INTO rol (id, descripcion) VALUES (1, 'ADMIN');
INSERT IGNORE INTO rol (id, descripcion) VALUES (2, 'OPERADOR');

-- ================================
-- Datos iniciales: usuario
-- ================================
-- Password: admin
INSERT IGNORE INTO usuario (id, nombre, apellido, documento, email, password, rol_id)
VALUES (1, 'Admin', 'Principal', '000000000000001', 'admin@mail.com', '$2b$12$s9rDNWKYU682Xq7ifzKoROYoEyvaU4lifZfNkJELH/5sQO1160BPC', 1);


