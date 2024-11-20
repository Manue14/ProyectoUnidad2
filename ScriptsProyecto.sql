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

DELIMITER $
create procedure filter_obras(in _titulo VARCHAR(100), in _autor_id INT, in _departamento_id INT, in _movimiento_id INT, in _categoria VARCHAR(50), in in_popular BOOLEAN)
begin
	select * FROM Obras
    set Rua = _rua, Numero_rua = _numero, Piso = _piso, CP = _cp, Localidade = _localidade
    where NSS = _nss;
end$
DELIMITER ;

INSERT INTO Autores (id,nombre,nacimiento, nacionalidad)
VALUES 
(0,'Anonimo', '2024-11-20', 'Española');

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
('Henri', 'Matisse', '1869-12-31', '1954-11-03', 'Francesa', 'https://example.com/matisse.jpg'),
('René', 'Magritte', '1898-11-21', '1967-08-15', 'Bélgica', 'https://example.com/magritte.jpg');

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

INSERT INTO Departamentos (nombre, descripcion)
VALUES
('Arte Egipcio', 'Estudio del arte producido en el antiguo Egipto, incluyendo monumentos, esculturas y jeroglíficos.'),
('Arte Europeo', 'Arte producido en Europa a lo largo de los siglos, con énfasis en las grandes corrientes artísticas como el Renacimiento, Barroco y Modernismo.'),
('Arte Asiático', 'Arte que abarca las tradiciones artísticas de Asia, incluyendo China, Japón, India, entre otros.'),
('Arte Islámico', 'Arte relacionado con las culturas islámicas, incluyendo la arquitectura, la cerámica y la caligrafía.'),
('Arte Africano', 'Arte de las diversas regiones de África, caracterizado por la escultura, las máscaras y las pinturas.'),
('Arte Precolombino', 'Arte de las civilizaciones de América antes de la llegada de los europeos, como los mayas, aztecas e incas.'),
('Arte Contemporáneo', 'Arte de los siglos XX y XXI, que incluye una amplia gama de estilos y medios artísticos.'),
('Arte Moderno', 'Movimiento artístico de finales del siglo XIX y principios del XX que rompe con las tradiciones clásicas y busca la innovación.'),
('Arte Renacentista', 'Movimiento artístico de Europa en el siglo XV y XVI que vuelve a inspirarse en las ideas y las formas clásicas griegas y romanas.'),
('Arte Barroco', 'Estilo artístico del siglo XVII caracterizado por el drama, la emoción y el detallismo ornamentado.');


INSERT INTO Obras (titulo, alto, ancho, imagen, popular, medio, categoria, fecha, descripcion, id_autor, id_departamento, id_movimiento)
VALUES
-- Obras de Pablo Picasso (Cubismo)
('Guernica', 3.5, 7.8, 'https://example.com/guernica.jpg', TRUE, 'Óleo sobre lienzo', 'Lienzo', '1937-01-01', 'Una de las obras más emblemáticas de Picasso que denuncia los horrores de la guerra.', 1, 2, 4),
('Les Demoiselles d\'Avignon', 2.44, 2.34, 'https://example.com/demoiselles.jpg', TRUE, 'Óleo sobre lienzo', 'Lienzo', '1907-01-01', 'Obra que marca el comienzo del cubismo, con figuras geométricas y una ruptura con la perspectiva tradicional.', 1, 2, 4),
('El viejo guitarrista', 1.22, 0.88, 'https://example.com/oldguitarist.jpg', TRUE, 'Óleo sobre lienzo', 'Lienzo', '1903-01-01', 'Obra del período azul, influenciada por el simbolismo y el realismo social, a pesar de su posterior asociación con el cubismo.', 1, 7, 2),

-- Obras de Leonardo da Vinci (Renacimiento)
('La Mona Lisa', 0.77, 0.53, 'https://example.com/monalisa.jpg', TRUE, 'Óleo sobre madera', 'Lienzo', '1503-01-01', 'El retrato más famoso del Renacimiento, famosa por la misteriosa sonrisa de la modelo.', 2, 9, 1),
('El Último Supper', 4.6, 8.8, 'https://example.com/lastsupper.jpg', TRUE, 'Tempera sobre yeso', 'Mural', '1495-01-01', 'Una de las representaciones más icónicas de la última cena de Jesucristo, con un fuerte enfoque en la emoción.', 2, 9, 1),
('San Juan Bautista', 0.69, 0.57, 'https://example.com/johnbaptist.jpg', TRUE, 'Óleo sobre lienzo', 'Lienzo', '1513-01-01', 'Obra que destaca la espiritualidad y la suavidad de las formas, características que serán adoptadas y amplificadas por el Barroco.', 2, 7, 2),
('La Virgen de las rocas', 1.21, 1.60, 'https://example.com/virgindelrocas.jpg', TRUE, 'Óleo sobre lienzo', 'Lienzo', '1486-01-01', 'Pese a sus orígenes renacentistas, la obra muestra una atmósfera de misterio y dramatismo, elementos que serán fundamentales en el Barroco.', 2, 9, 2),
-- Obras de Michelangelo (Renacimiento)
('David', 5.17, 2.00, 'https://example.com/david.jpg', TRUE, 'Mármol', 'Mármol', '1504-01-01', 'Escultura monumental de David, representando la belleza del cuerpo humano en el Renacimiento.', 3, 9, 1),
('La Creación de Adán', 3.00, 5.00, 'https://example.com/creation.jpg', TRUE, 'Fresco', 'Mural', '1512-01-01', 'Parte de la decoración de la Capilla Sixtina, que muestra el momento en que Dios da vida a Adán.', 3, 9, 1),

-- Obras de Vincent van Gogh (Postimpresionismo)
('La noche estrellada', 0.74, 0.92, 'https://example.com/starrynight.jpg', TRUE, 'Óleo sobre lienzo', 'Lienzo', '1889-01-01', 'Una de las obras más reconocidas de Van Gogh, destacando el movimiento y la emoción de un cielo nocturno.', 4, 7, 3),
('Los girasoles', 0.92, 0.73, 'https://example.com/sunflowers.jpg', TRUE, 'Óleo sobre lienzo', 'Lienzo', '1888-01-01', 'Serie de pinturas de girasoles, famosas por su vibrante colorido y el uso de pinceladas rápidas.', 4, 7, 3),

-- Obras de Claude Monet (Impresionismo)
('Impresión, sol naciente', 0.48, 0.63, 'https://example.com/impression.jpg', TRUE, 'Óleo sobre lienzo', 'Lienzo', '1872-01-01', 'Obra que dio nombre al movimiento impresionista, capturando la luz y la atmósfera del puerto de Le Havre.', 5, 7, 3),
('Nenúfares', 0.89, 0.99, 'https://example.com/nenufares.jpg', TRUE, 'Óleo sobre lienzo', 'Lienzo', '1906-01-01', 'Serie que representa los nenúfares en su jardín de Giverny, capturando la luz y la reflexión en el agua.', 5, 7, 3),

-- Obras de Frida Kahlo (Surrealismo / Expresionismo)
('Las dos Fridas', 1.74, 1.73, 'https://example.com/twopacas.jpg', TRUE, 'Óleo sobre lienzo', 'Lienzo', '1939-01-01', 'Autorretrato dividido en dos personalidades, representando su dolor y su identidad.', 6, 7, 5),
('La columna rota', 0.99, 0.79, 'https://example.com/brokencolumn.jpg', TRUE, 'Óleo sobre lienzo', 'Lienzo', '1944-01-01', 'Una representación de su sufrimiento físico y emocional tras un accidente.', 6, 7, 5),

-- Obras de Salvador Dalí (Surrealismo)
('La persistencia de la memoria', 0.24, 0.33, 'https://example.com/persistence.jpg', TRUE, 'Óleo sobre lienzo', 'Lienzo', '1931-01-01', 'Famosa por sus relojes derretidos, una reflexión sobre el tiempo y la realidad distorsionada.', 7, 7, 5),
('El gran masturbador', 0.75, 0.95, 'https://example.com/greatmasturbator.jpg', TRUE, 'Óleo sobre lienzo', 'Lienzo', '1929-01-01', 'Obra surrealista que refleja el subconsciente y los deseos reprimidos.', 7, 7, 5),

-- Obras de Georgia O'Keeffe (Arte Moderno)
('Flor blanca', 0.61, 0.91, 'https://example.com/whiteflower.jpg', TRUE, 'Óleo sobre lienzo', 'Lienzo', '1932-01-01', 'Obra que explora la belleza natural de las flores, con un enfoque en la forma y el color.', 8, 7, 8),
('Cielo azul', 0.81, 1.03, 'https://example.com/blue-sky.jpg', TRUE, 'Óleo sobre lienzo', 'Lienzo', '1936-01-01', 'Representación abstracta del cielo con una paleta de colores vibrantes y formas simplificadas.', 8, 7, 8),

-- Obras de Henri Matisse (Fauvismo)
('La danza', 1.72, 1.74, 'https://example.com/dance.jpg', TRUE, 'Óleo sobre lienzo', 'Lienzo', '1909-01-01', 'Una obra representativa del Fauvismo, donde el color y la forma juegan un papel fundamental.', 9, 7, 6),
('La alegría de vivir', 1.65, 1.98, 'https://example.com/joyoflife.jpg', TRUE, 'Óleo sobre lienzo', 'Lienzo', '1905-01-01', 'Una obra vibrante que exalta la vitalidad y la exuberancia de la vida.', 9, 7, 6),
-- Obras de René Magritte
('La clef des champs', 1.10, 1.30, 'https://example.com/laclefdeschamps.jpg', TRUE, 'Óleo sobre lienzo', 'Lienzo', '1936-01-01', 
 'Obra surrealista de René Magritte que muestra una escena  en el campo que invita a la reflexion.', 12, 2,5),
 ('El hijo del hombre',116,89 ,'https://example.com/elhijodelhombre.jpg',TRUE,'Óleo sobre lienzo','Lienzo',1964,'La pintura se compone de un hombre con abrigo, corbata roja 
 y bombín de pie delante de un muro. Más allá se ve el mar y un cielo nublado.', 12, 2,5);

#drop DATABASE Coleccion;