# Entrega taller 1 AREP-101

El siguiente readme tiene la finalidad de ayudar con la instalacion del
primer taller de la materia 'Arquitecturas Empresariales', siendo este
un servidor web que soporte múlltiples solicitudes seguidas no concurrentes.

# Pre-requisitos
- maven (esto debido a una libreria utilizada para manejo de objetos json)
- navegador web (con la finalidad de ver el servidor web)
- un IDE de preferencia (para poder inicializar el servidor)

## Instalacion

1) Primero que todo, hay que clonar el repositorio en el que se encuentra actualmente, es decir, este repositorio a una ubicacion de su elección, ya sea en descargas, escritorio o junto a demas documentos.

![archivo descomprimido](/src/resources/carpeta.png)

2) Una vez descargada la carpeta, esta se necesita abrir con un entorno de desarrollo integrado o 'IDE', para este ejemplo se utilizo 'visual studio code', dando el siguiente resultado 

![taller en ide](/src/resources/ide.png)

3) Continuando dentro del IDE elegido, procedemos a inicializar el archivo, en este caso se puede realizar con click derecho y eligiendo la opción 'run java' como se muestra en la imagen. Tambien es posible iniciar el servidor con un boton ubicado en la zona superior izquierda que se asemeja a un triangulo.

![iniciar servidor](/src/resources/correr%20servidor.png)

![boton](/src/resources/boton.png)

4) Una vez inicializado el servidor web, deberia soltar en la consola el siguiente mensaje:

![inicio correcto](/src/resources/inicio.png)

5) Ahora nos dirigimos al navegador web de nuestra preferencia (para este ejemplo se uso microsoft edge) y digitamos en la barra de url el siguiente url:

```
http://localhost:5000/index.html
```
y le damos al boton enter.

![link](/src/resources/enlace.png)

6) Si se han seguido los anteriores pasos de forma correcta, deberia verse en la pantalla la siguiente pagina, haciendo una imitiación de un formulario de inscripción para el arima kinen, una de las carreras de caballos mas prestigiosas de Japon 

![pagina arima kinen](/src/resources/pagina%20funcionando.png)

7) Al final de esta, hay un pequeño formulario para inscribir el caballo junto a otros dos datos (con la finalidad de tener mas semejanza a un formulario de verdad) , se diligencian a gusto propio del siguiente modo:

![inscripcion](/src/resources/inscripcion.png)

y una vez dado al boton 'Registrar caballo' deberia salir el siguiente mensaje:

```
(nombre del caballo) ha sido registrado exitosamente
```

![mensaje](/src/resources/mensaje.png)

mostrando la funcionalidad del servidor web para manejar servicios REST

## Referencias

La imagen y video utilizados no son de autoria propia, si no que se tomaron de 'netkeiba', una pagina especializada en las carreras de caballos de japon.

#### Imagen
- https://en.netkeiba.com/race/shutuba.html?race_id=202510020811&rf=race_toggle_menu
#### Video
- https://en.netkeiba.com/library/detail.html?no=147

Icono utilizado para el header del servidor, este es de uso libre pero requiere su debida retribucion al autor.
#### Icono
- https://www.flaticon.es/icono-gratis/silueta-de-caballo-saltando-hacia-la-vista-lateral-izquierda_33348?term=caballo&page=1&position=12&origin=tag&related_id=33348

### Autor:
Nicolás Pachón Unibio


