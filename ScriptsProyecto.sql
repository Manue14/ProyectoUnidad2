CREATE DATABASE Coleccion;
use Coleccion;

CREATE TABLE Departamentos (
	id INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
    nombre VARCHAR(30) NOT NULL,
    descripcion VARCHAR(150) NOT NULL
);

CREATE TABLE Movimientos (
	id INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
    nombre VARCHAR(20) NOT NULL,
    fecha_inicio DATE NOT NULL,
    fecha_fin DATE,
    descripcion VARCHAR(150) NOT NULL
);

CREATE TABLE Autores (
	id INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
    nombre VARCHAR(30) NOT NULL,
    apellido1 VARCHAR(30),
    apellido2 VARCHAR(30),
    nacimiento DATE NOT NULL,
    fallecimiento DATE,
    nacionalidad VARCHAR(20) NOT NULL,
    foto VARCHAR(255)
);

CREATE TABLE Autores_Movimientos (
	id_autor INT NOT NULL,
    id_movimiento INT NOT NULL
);

CREATE TABLE Obras (
	id INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
    titulo VARCHAR(100) NOT NULL,
    alto FLOAT NOT NULL,
    ancho FLOAT NOT NULL,
    imagen VARCHAR(255) NOT NULL,
    popular BOOLEAN NOT NULL,
    medio VARCHAR(50) NOT NULL,
    categoria ENUM('Lienzo', 'Papel', 'Tejido', 'Metal', 'Arcilla/Barro', 'Cerámica', 'Madera', 'Porcelana', 'Mármol', 'Mural'),
    fecha VARCHAR(21),
    descripcion varchar(150) NOT NULL,
    id_autor INT NOT NULL,
    id_departamento INT NOT NULL,
    id_movimiento INT
);

ALTER TABLE Obras ADD FOREIGN KEY (id_departamento) REFERENCES Departamentos(id) ON UPDATE NO ACTION ON DELETE NO ACTION;
ALTER TABLE Obras ADD FOREIGN KEY (id_movimiento) REFERENCES Movimientos(id) ON UPDATE NO ACTION ON DELETE NO ACTION;
ALTER TABLE Obras ADD FOREIGN KEY (id_autor) REFERENCES Autores(id) ON UPDATE NO ACTION ON DELETE NO ACTION;
ALTER TABLE Autores_Movimientos ADD FOREIGN KEY (id_autor) REFERENCES Autores(id) ON UPDATE NO ACTION ON DELETE NO ACTION;
ALTER TABLE Autores_Movimientos ADD FOREIGN KEY (id_movimiento) REFERENCES Movimientos(id) ON UPDATE NO ACTION ON DELETE NO ACTION;

DELIMITER $
create procedure filter_obras(in _titulo VARCHAR(100), in _autor_id INT, in _departamento_id INT, in _movimiento_id INT, in _categoria VARCHAR(50), in in_popular BOOLEAN)
begin
	select * FROM Obras
    set Rua = _rua, Numero_rua = _numero, Piso = _piso, CP = _cp, Localidade = _localidade
    where NSS = _nss;
end$
DELIMITER ;

#drop DATABASE Coleccion;