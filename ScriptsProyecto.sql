drop DATABASE if exists Coleccion;
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


INSERT INTO Autores (nombre, apellido1, apellido2, nacimiento, fallecimiento, nacionalidad, foto)
VALUES 
('Pablo', 'Ruiz', 'Picasso', '1881-10-25', '1973-04-08', 'Española', 'https://example.com/picasso.jpg'),
('Leonardo', 'di Ser', 'Piero', '1452-04-15', '1519-05-02', 'Italiana', 'https://example.com/davinci.jpg'),
('Michelangelo', 'di Lodovico', 'Buonarroti', '1475-03-06', '1564-02-18', 'Italiana', 'https://example.com/michelangelo.jpg');

INSERT INTO Autores (nombre, apellido1, nacimiento, fallecimiento, nacionalidad, foto)
VALUES 
('Vincent', 'van Gogh', '1853-03-30', '1890-07-29', 'Neerlandesa', 'https://example.com/vangogh.jpg'),
('Claude', 'Monet', '1840-11-14', '1926-12-05', 'Francesa', 'https://example.com/monet.jpg'),
('Frida', 'Kahlo', '1907-07-06', '1954-07-13', 'Mexicana', 'https://example.com/kahlo.jpg'),
('Salvador', 'Dalí', '1904-05-11', '1989-01-23', 'Española', 'https://example.com/dali.jpg'),
('Georgia', 'O\'Keeffe', '1887-11-15', '1986-03-06', 'Estadounidense', 'https://example.com/okeeffe.jpg'),
('Andy', 'Warhol', '1928-08-06', '1987-02-22', 'Estadounidense', 'https://example.com/warhol.jpg'),
('Henri', 'Matisse', '1869-12-31', '1954-11-03', 'Francesa', 'https://example.com/matisse.jpg');

INSERT INTO Movimientos (nombre, fecha_inicio, fecha_fin, descripcion)
VALUES
('Renacimiento', '1400-01-01', '1600-01-01', 'Movimiento cultural europeo centrado en el humanismo y las artes.'),
('Barroco', '1600-01-01', '1750-01-01', 'Estilo caracterizado por el drama, la emoción y el detalle exuberante.'),
('Impresionismo', '1860-01-01', '1886-01-01', 'Arte enfocado en la luz, el color y los paisajes.'),
('Cubismo', '1907-01-01', '1920-01-01', 'Movimiento que rompió con la perspectiva tradicional.'),
('Surrealismo', '1924-01-01', '1945-01-01', 'Exploró el subconsciente y los sueños en el arte.'),
('Expresionismo', '1905-01-01', '1933-01-01', 'Arte que expresa emociones intensas y subjetivas.'),
('Romanticismo', '1780-01-01', '1850-01-01', 'Movimiento enfocado en la emoción, la naturaleza y el individualismo.'),
('Arte Pop', '1950-01-01', '1970-01-01', 'Arte inspirado en la cultura popular y los medios de comunicación.'),
('Futurismo', '1909-01-01', '1944-01-01', 'Celebración del movimiento, la velocidad y la tecnología en el arte.'),
('Neoclasicismo', '1750-01-01', '1820-01-01', 'Retorno a los valores estéticos de la antigüedad clásica.');


INSERT INTO Autores_Movimientos (id_autor, id_movimiento)
VALUES
(1, 4), -- Pablo Picasso -> Cubismo
(2, 1), -- Leonardo da Vinci -> Renacimiento
(3, 1), -- Michelangelo Buonarroti -> Renacimiento
(4, 6), -- Vincent van Gogh -> Expresionismo
(5, 3), -- Claude Monet -> Impresionismo
(6, 7), -- Frida Kahlo -> Romanticismo
(7, 5), -- Salvador Dalí -> Surrealismo
(8, 7), -- Georgia O'Keeffe -> Romanticismo
(9, 8), -- Andy Warhol -> Arte Pop
(10, 6); -- Henri Matisse -> Expresionismo


#drop DATABASE Coleccion;