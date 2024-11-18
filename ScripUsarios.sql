drop database if exists usuariosMuseo;
create database usuariosMuseo;

use usuariosMuseo;

CREATE TABLE permisos (
    idPermiso INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(255) NOT NULL
);


CREATE TABLE users (
    idUser INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(255) NOT NULL,
    contraseña VARCHAR(255) NOT NULL,
    idPermiso INT,
    FOREIGN KEY (idPermiso) REFERENCES permisos(idPermiso)
);

INSERT INTO permisos (idPermiso, nombre) VALUES
(1, 'Admin'),
(2, 'Usuario base');

INSERT INTO users (nombre, contraseña, idPermiso) 
VALUES ('admin', '240be518fabd2724ddb6f04eeb1da5967448d7e831c08c8fa822809f74c720a9', 1);

#admin123
