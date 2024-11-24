# Index

- [1. Introducción](#1-introducción)
- [2. Manual técnico para desarrolladores](#2-Manual-técnico-para-desarrolladores)
-   [2.1. Modelo](##2-1-Modelo)
-   [2.2. Vista](##2-2-Vista)
-   [2.3. Controlador](##2-3-Controlador)
- [3. Manual de usuario](#3-Manual-de-usuario)
  - [3.1. Administrador](###3-1-Administrador)
  - [3.2. Usuario](###3-2-Usuario)   
- [4. Reparto de las tareas](#4-Reparto-de-las-tareas)
- [5. Extras realizados](#5-Extras-realizados)
- [6. Propuestas de mejora](#6-Propuestas-de-mejora)
- [7. Conclusiones y opinión del trabajo realizado, dedicación temporal y cualificación estimada](#7-Conclusiones-y-opinión-del-trabajo-realizado,-dedicación-temporal-y-cualificación-estimada)

# 1. Introducción

Siguiendo la temática del primer proyecto decidimos hacer una base de datos sobre obras de arte, añadiendo datos como sus autores o departamentos, aquí su diagrama E/R:

![ERMuseo.png](imgs/ERMuseo.png)

A mayores creamos una base de datos en la que almacenamos los usuarios, sus contraseñas almacenadas y su rol. Su diagrama E/R

![ERUser.png](imgs/ERUser.png)

# 2. Manual técnico para desarrolladores

La aplicación utiliza dos bases de datos, una para los datos de la colección de arte y otra para la información de las cuentas. Los archivos para la creación de ambas se encuentran dentro del proyecto e incluyen la creación de las tablas necesarias, la inserción de los datos iniciales (entre los que destacar la cuenta de administrador, **usuario:admin contraseña:admin123**) y la creación de procediminetos almacenados para algunas de las consultas así como funciones y triggers para facilitar la administración de la base de datos.

La estructura del proyecto **sigue el patron Modelo-Vista-Controlador**, a continuación hablaremos de cada una de las partes.

## 2.1. Modelo 
  El paquete modelo inculye, clases para manejar la conexión a las BD, las clases para los elementos de la BD, una clase para mappear la conversión de los elementos, unas clases que representan los filtros de busqueda para obras y autores y enums que controlan ciertos atributos.

  Al trabajar con dos bases de datos utilizamos dos clases distintas, **DBConnector y UserModel**, en la primera tenemos todos los metodos necesarios para trabajar con la parte de la colección. Desde añadir nuevas obras hasta seleccionar movimientos por su id o todas las nacionalidades distintas. La lista completa de metodos sería:
  
|                       | **MÉTODOS DE LA CLASE DBConnector**                                                                                                                                                 |
| --------------------- | ----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |  
| **CREACION**          |                                                                                                                                                                                     |
| createAutor()         | Recibe un objeto Autor y lo añade a la base de datos                                                                                                                                |
| createObra()          | Recibe un objeto Obra y lo añade a la base de datos                                                                                                                                 |
| **LECTURA**           |                                                                                                                                                                                     |
| getAllFromTable()     | (*deprecated*) Recibe un enum Table y devuelve todos los registros de la tabla correspondiente                                                                                      |
| getAllAutores()       | Devuelve un ArrayList\<Autor> que contiene los objetos equivalentes a cada registro de la tabla Autores.                                                                            |
| getAllDepartamentos() | Devuelve un ArrayList\<Depatamento> que contiene los objetos equivalentes a cada registro de la tabla Departamentps.                                                                |
| getAllMovimientos()   | Devuelve un ArrayList\<Movimiento> que contiene los objetos equivalentes a cada registro de la tabla Movimientos.                                                                   |
| getAllObras()         | Devuelve un ArrayList\<Obra> que contiene los objetos equivalentes a cada registro de la tabla Obras.                                                                               |
| getAutorById()        | Recibe un id (int) y devuelve un objeto Autor con los datos del registro  que coincida con el id.                                                                                   |
| getDepartamentoById() | Recibe un id (int) y devuelve un objeto Departamento con los datos del registro  que coincida con el id.                                                                            |
| getMovimientoById()   | Recibe un id (int) y devuelve un objeto Movimiento con los datos del registro  que coincida con el id.                                                                              |
| getObraById()         | Recibe un id (int) y devuelve un objeto Obra con los datos del registro  que coincida con el id.                                                                                    |
| getNacionalidades()   | Devuelve un ArrayList\<String> con todas las nacionalidades distintas que aparecen en la tabla Autores.                                                                             |
| getIdAutorPorNombre() | Recibe un nombre (String) y devuelve el id (int) del autor cuyo nombre coincida en la tabla Autores.                                                                                |
| filterAutores()       | Recibe un objeto QueryFieldsObjectAutor y devuelve un ArrayList\<Autor> con los objetos equivalentes a todos los registros que coincidan con los criterios de búsqueda.             |
| filterObras()         | Recibe un objeto QueryFieldsObjectObra y devuelve un ArrayList\<Obra> con los objetos equivalentes a todos los registros que coincidan con los criterios de búsqueda. Para realizar el filtrado en la base de datos se usa el procedimiento "filter_obras" sobre él que se hablará más adelante.               |
| **ACTUALIZACIÓN**     |                                                                                                                                                                                     |
| updateAutor()         | Recibe un objeto Autor y actualiza el registro con el mismo id con los datos proporcionados por el objeto. Devuelve true o false dependiendo de si se ha realizado la modificación. |
| updateObra()          | Recibe un objeto Obra y actualiza el registro con el mismo id con los datos proporcionados por el objeto. Devuelve true o false dependiendo de si se ha realizado la modificación.  |
| **ELIMINACIÓN**       |                                                                                                                                                                                     |
| deleteAutor()         | Recibe un objeto Autor y elimina el registro con el mismo id. Devuelve true o false dependiendo de si se ha realizado la modificación.                                              |
| deleteObra()          | Recibe un objeto Obra y elimina el registro con el mismo id. Devuelve true o false dependiendo de si se ha realizado la modificación.                                               |
| **COMPROBACIÓN**      |                                                                                                                                                                                     |
| checkIfIdExists()     | Recibe un id (int) y un nombre de tabla (String) y comprueba si el id existe en esa tabla. Devuelve un int, 1 si existe, 0 si no existe y -1 en caso de excepción.                  |


  
  Aquellas **clases que representan los elementos de las tablas de la BD**, además de sus constructores, getters y setters, cuentan con metodos para su comparación. Esto se debe a la necesidad de poder saber si dos instancias diferentes contienen los mismos datos sin que su identificador propio de Java devuelva la comparación como false.
  
  ![2](https://github.com/user-attachments/assets/dc4145e7-570e-4cb2-8f06-5b68eda990ae)
  
  **Estas clases son:**
  
  - Autor
  - Departamento 
  - Movimiento
  - Obra
    


  La **clase Mapper** cuenta con un metodo para convertir cada tipo de registro en su objeto correspondiente. Estos metodos se usan dentro de las consultas a la BD de la clase BDConnector para poder devolver ArrayList de objetos con los que es más fácil trabajar, para ello los métodos reciben un objeto ResultSet. También cuenta con métodos para controlar el proceso contrario, pasar un objeto Java a un registro, para esto reciben un PreparedStatement y un objeto del tipo que corresponda. Todos los métodos de esta clase son estáticos, por lo que no es necesario una instancia de la clase para utilizarlos.
  
  Aquí tenemos un ejemplo de mappeo de ResultSet a objeto Java:
  ```Java
  public static Obra mapObra(ResultSet rs) throws SQLException {
        byte[] foto = rs.getBlob("imagen")==null?null:rs.getBlob("imagen").getBytes(1, (int) rs.getBlob("imagen").length());
        return new Obra(rs.getInt("id"), rs.getString("titulo"), rs.getFloat("alto"),
                rs.getFloat("ancho"), foto, rs.getBoolean("popular"),
                rs.getString("medio"),
                Categoria.getByValor(rs.getObject("categoria").toString()), rs.getString("fecha"),
                rs.getString("descripcion"), rs.getInt("id_autor"),
                rs.getInt("id_departamento"), rs.getInt("id_movimiento"));
    }
  ```
  
  Y un ejemplo de mappeo objeto Java a un PreparedStatement:
  ```Java
  public static void bindObraUpdateQuery(PreparedStatement ps, Obra obra) throws SQLException {
        ps.setString(1, obra.getTitulo());
        ps.setFloat(2, obra.getAlto());
        ps.setFloat(3, obra.getAncho());
        ps.setBytes(4, obra.getImg());
        ps.setBoolean(5, obra.isPopular());
        ps.setString(6, obra.getMedio());
        ps.setString(7, obra.getCategoria().getValor());
        ps.setString(8, obra.getFecha());
        ps.setString(9, obra.getDescripcion());
        ps.setInt(10, obra.getId_autor());
        ps.setInt(11, obra.getId_departamento());
        ps.setInt(12, obra.getId_movimiento());
        ps.setInt(13, obra.getId());
    }
  ```

Las **clases utilizadas para filtrar obras y autores son QueryFieldsObjectObra y QueryFieldsObjectAutor**, ya que los parámetros de búsqueda no cubren todos los atributos de los objetos Obra y Autor se utilizan estas clases  para representar los filtros y realizar las consultas necesarias. También hacen más cómodo el control de valores nulos introducidos por el usuario a la hora de filtrar, es decir, es posible que el usuario no elija ningún valor en un determinado campo de filtro y para evitar posibles errores al tratar con valores nulos establecemos valores por defecto en la clases QueryFieldsObjects que son tratados como procede en la propia base de datos.

**Triggers**: En la base de datos creamos un trigger que nos ayuda a borrar autores. Debido a a que el id de los autores está referenciado en otras tablas como una foreign key el borrado de un autor podría resultar en problemas, para eso antes de borrar al autor borramos todas sus otras referencias en las tablas correspondientes.

**Funciones**: Creamos una función "count_obras_by_autor_id(id)" la cual, dada un id de autor, no devuelve la cantidad de obras relacionadas con tal id.

**Procedimientos**:
    - del_autor_if_not_obras(id): Es llamado en el método de borrar una obra, comprueba si el autor de la obra borrada no tiene ninguna otra obra relacionada en la base de datos (para eso usa la función "count_obras_by_autor_id(id)") y sí efectivamente no tiene ninguna lo borra.
    - filter_obras(titulo, autor, departamento_id, movimiento_id, categoria, popular): Dependiendo de si los parámetros que recibió son considerados como nulos o no va construyendo un sentencia SQL dinámica para el filtrado de obras de la base de datos.

**Los Enums Categoria y Tabla** se utilizan a lo largo del código ya que son opciones preestablecidas que no admiten nuevos valores.

Una última cosa que cabe resaltar es el **tratamiento de imágenes**. Cada obra y algunos autores tienen una imagen relacionada que es almacenada en la propia base de datos como un LARGEBLOB. En el mapeo desde la base de datos a un objeto Java ese LARGEBLOB es convertido a un array de bytes lo cual facilita luego el mostrado de tales imágenes en la vista ya que la clase Image de JavaFX acepta un InputStream como parámetro en su constructor el cual es fácil de obtener a partir de un array de bytes. Para bindear la imagen del objeto Java a un PreparedStatement se usa el método setBytes() que acepta directamente un array de bytes para introducirlo a una columna tipo LARGEBLOB.

## 2.2. Vista 

  La parte de vista incluye las distintas interfaces que encontramos a lo largo de la aplicacción. Son un total de 6 escenas, en la siguiente imagen podemos ver un esquema de como funciona el flujo entre ellas.
  
  ![Drawing 2024-11-24 11 46 57 excalidraw](https://github.com/user-attachments/assets/bae6976f-68dc-4848-a736-45646462765a)

## 2.3. Controlador

El paquete controlador cuenta con una clase para cada una de las vistas además de una clase adicional para controlar los alerts que se usan para notificar al usuario.

### AccesoController

Las funciones más importantes de esta clase son validar las credenciales y encriptar las contraseñas para una mejor seguridad. A continuación podemos echar un vistazo al codigo que se encarga de esto.

```Java
//VALIDACION
 @FXML  
    public void validarCredenciales() throws IOException {  
  
        //valores que metió el usuario  
        String usernameAcceso = usernameField.getText();  
        String passwordAcceso = passwordField.getText();  
        String passwordaAccesoHaseada = hashPassword(passwordAcceso);  
  
        UserModel model = new UserModel();  
  
  
        int resultado = model.validarCredenciales(usernameAcceso, passwordaAccesoHaseada);  
  
        // Mostrar el mensaje de acuerdo al resultado  
        switch (resultado) {  
            case 0:  
                messageLabel.setText("Acceso exitoso");  
                int permiso = UserModel.permisosUser;  
                if (permiso == 1) {  
                    HelloApplication.setRoot("admin");  
                } else if (permiso == 2) {  
                    HelloApplication.setRoot("user");  
                }  
                //HelloApplication.setRoot("home");  
                break;  
            case 1:  
                messageLabel.setText("No hay usuario con ese nombre");  
                break;  
            case 2:  
                messageLabel.setText("Error: Contraseña incorrecta");  
                break;  
            default:  
                messageLabel.setText("Error en la conexión a la base de datos");  
                break;  
        }  
  
}  
  
//ENCRIPTACION  
private static String hashPassword(String password) {  
    try {  
        MessageDigest digest = MessageDigest.getInstance("SHA-256");  
  
        // Hashear la contraseña  
        byte[] hashBytes = digest.digest(password.getBytes());  
        StringBuilder hexString = new StringBuilder();  
        for (byte b : hashBytes) {  
            String hex = Integer.toHexString(0xff & b);  
            if (hex.length() == 1) {  
                hexString.append('0'); // Agregar un cero si es necesario  
            }  
            hexString.append(hex);  
        }  
  
        // Retornar el hash como cadena hexadecimal  
        return hexString.toString();  
  
  
    } catch (NoSuchAlgorithmException e) {  
        throw new RuntimeException(e);  
    }  
}
```
Como se pudo ver en el esquema anterior también se llama desde aquí a las vistas de crear usuario, administrador o usuario según corresponda.

### CrearUserController
La creación de usuario es lo más importante de esta clase, para ello recoge los datos que el usuario introduce en la vista y realiza las comprobaciones necesarias, como asegurarse de que los campos no están vacíos y que las contraseñas coinciden para evitar fallos. Si el formulario es correcto procede a introducir un nuevo registro de usuario en la base de datos, no sin antes encriptar la contraseña.

### AdminController
La interfaz del administrador cuenta con muchas funciones, ya que en su vista permite al usuario la visualización de los datos de las tablas Obras y Autores y las acciones principales de CRUD sobre las mismas.
Su método de inicialización carga los datos y la configuración de las tablas así como los valores de las ComboBox que se utilizan para el filtrado de autores y obras. Después los distintos listeners controlan las acciones de:
- Cerrar sesión
- Cerrar aplicación
- Exportar obras
- Exportar autores
- Buscar obras
- Buscar autores
- Añadir Obra
- Añadir Autor
- Editar Obra
- Editar Autor
- Eliminar Obra
- Eliminar Autor
- Mostrar dialog para añadir/modificar obras
- Mostrar dialog para añadir/modificar autores

### AutorDialogController y ObraDialogController
Ambos dialog funcionan de una forma muy similar, con un objeto de la clase correspondiente cuyos datos van ligados a los campos del formulario. Este objeto se usará para añadir un nuevo elemento o para modificar uno ya existente (paro lo que previamente se cargan los datos del objeto en el formulario). El controller cuenta con un boolean que controla en que modo se encuentra el dialog (creación o modificación) que cambia según quien haya llamado al dialog. Este modo determinará las acciones que se toman en su inicialización.

### UserController
Las funciones del UserController son más limitadas con respecto a su contra parte de administrador, pero la lista incluye:
- Buscar obras
- Exportar datos de la obra buscada
- Cerrar sesión
- Cerrar programa

### AlertMaker
Esta clase cuenta con metodos para crear y mostrar distintos tipos de alert, como los de Error, Informacion y Confirmación. Todos responden a esta estructura:

```Java
public static void showWarning(String titulo, String mensaje){
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.show();
        System.out.println(mensaje);
    }
```


# 3. Manual de usuario

Al iniciar la aplicación lo primero que encontramos es la ventana de Log in, donde podremos acceder con las credenciales de administrador o crear unas nuevas credenciales de usuario.

![1](https://github.com/user-attachments/assets/2758a29e-9203-4910-8b5f-60bb4c9b7ff1)

Si decidimos crear un nuevo usuario necesitaremos introducir un nombre y la contraseña (dos veces por motivos de seguridad).

![2](https://github.com/user-attachments/assets/9ae163b6-7b43-4c18-8c36-ba40537120ff)
![1_1](https://github.com/user-attachments/assets/e9aef00a-3aa8-4b56-b2d7-f05ce8c72c7a)

### 3.1. Administrador
Nos centraremos primero en la vista de **Administrador**. Al logearse con este tipo de usuario podremos ver una vista de tabla con los campos más importantes de las obras y de los autores que estén registrados en la colección. 

![4](https://github.com/user-attachments/assets/a9ce9e34-e47b-432a-b56a-3243025c0b1a)

Esta tabla puede ser filtrada utilizando los parámetros de la parte superior, o ordenada ascendente o descendentemente en los headers de las columnas. 

![10](https://github.com/user-attachments/assets/88498ff3-0996-451d-8ba3-2408f5d46074)
![18](https://github.com/user-attachments/assets/dc270670-3def-4627-b7ed-1fe93ee241f5)

La vista en tabla también permite la selección de un registro para su eliminación o modificación.

![8](https://github.com/user-attachments/assets/a832dc12-39d9-4dd6-8ba0-84b585097082)
![9](https://github.com/user-attachments/assets/be42d64d-e839-4b1c-baab-d8c3615fd335)
![19](https://github.com/user-attachments/assets/517b6937-d177-4481-9359-cbf694dfeabb)

Además podremos añadir nuevos elementos con el botón añadir, que al igual que la opción de modificar abrirá una ventana adicional con los campos que deberemos rellenar.

![7](https://github.com/user-attachments/assets/fd9dd144-69a3-4863-9dae-1fdf2788b373)

El cambio entre la tabla de obras y autores (así como sus filtros) puede realizarse mediante las pestañas de la parte superior izquierda.

![13](https://github.com/user-attachments/assets/7e363efc-1ae3-43c0-8964-ef2599da9d2f)

Por último encontramos la barra de menú, donde podremos encontrar opciones para cerrar sesión o salir del programa. En la sección de herramientas podremos exportar a formato JSON los datos de las tablas de obra o autor. Los datos exportados coinciden con los registros mostrados en la tabla, por lo que podremos usar los filtros de búsqueda para limitar las obras o autores que queremos exportar.

![5](https://github.com/user-attachments/assets/a638a8d2-b2fb-4045-8778-2b85b3a308a0)
![6](https://github.com/user-attachments/assets/bc4d051e-ec32-4d70-a0b9-60265e229dd4)

### 3.2. Usuario 
Registrarnos con el rol de usuario nos llevará a una ventana en la que podremos realizar consultas filtradas por distintos campos a la base de datos. El primer resultado que coincida con nuestros parámetros será mostrado en la parte inferior con sus datos más relevantes.

![14](https://github.com/user-attachments/assets/41604c28-f0fd-4a15-9100-6df15d7feed3)
![15](https://github.com/user-attachments/assets/7b53ee42-6bf9-4091-a148-fda8e52c0640)
![17](https://github.com/user-attachments/assets/b6a43ea9-13ea-4989-aaf2-e77e2b59d041)

La barra de menú nos permitirá cerrar sesión, cerrar la aplicación y exportar los datos de la obra que hayamos buscado.

![16](https://github.com/user-attachments/assets/a63e4914-fc52-4b5e-8414-0f398337e47d)


# 4. Explicación del reparto de las tareas entre ambos integrantes.

#### Iago
- Login
- DB usuarios
- Filtrado de obras y autores
- Datos DB coleccion
- Toolbar archivo
- Escribir documentación


#### Manuel
- Filtrado de obras y autores
- Filtrado de obras y autores
- DB coleccion
- Clases de los objetos Obra,autores...
- Escribir documentación


#### Samuel

- Interfaces
- Eliminar autores y obras
- Conexión de vista y modelo a través de los controladores
- Exportación a JSON
- Escribir documentación

# 5. Extras realizados

- **Login:** La aplicación cuenta con un sistema de login consultando a una DB con todos los usuarios, de ahí se coge su rol (admin o base) y se lleva al usuario a la ventana correspondiente. Además en el propio login puedes crear una cuenta en ese mismo momento

# 6. Propuestas de mejora

- Refactorización de código:
    - Superclase para las clases QueryFieldsObjectAutor y QueryFieldsObjectObras para evitar repetición de código.
    - Superclase para las clases ObraDialogController y AutorDialogController para evitar repetición de código.  
- Controlar mejor el funcionamiento de la aplicación cuando trata con valores nulos tanto desde el usuario como desde la base de datos.
- Añadir comprobación de campos obligatorios a la hora de crear autores y obras desde la interfaz gráfica.
- Crear una clase que se encargué de trabajar con las imágenes ya que puede llegar a ser un proceso bastante complejo o engorroso.
- Paginación de obras en la vista de usuario no administrador.
- Crear una pestaña para mostrar información más completa de obras y autores al clickar sobre sus registros en la tabla de resultados.
  
# 7. Conclusiones y opinión del trabajo realizado, dedicación temporal y cualificación estimada
