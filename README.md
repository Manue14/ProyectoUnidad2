# Index

- [1. Introducción](#1._introducción)

# 1. Introducción

Siguiendo la temática del primer proyecto decidimos hacer una base de datos sobre obras de arte, añadiendo datos como sus autores o departamentos, aquí su diagrama E/R:

![ERMuseo.png](imgs/ERMuseo.png)

A mayores creamos una base de datos en la que almacenamos los usuarios, sus contraseñas almacenadas y su rol. Su diagrama E/R

![ERUser.png](imgs/ERUser.png)


# 5. Extras realizados

- **Login:** La aplicación cuenta con un sistema de login consultando a una DB con todos los usuarios, de ahí se coge su rol (admin o base) y se lleva al usuario a la ventana correspondiente. Además en el propio login puedes crear una cuenta en ese mismo momento
