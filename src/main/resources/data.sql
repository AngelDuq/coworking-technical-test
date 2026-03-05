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


