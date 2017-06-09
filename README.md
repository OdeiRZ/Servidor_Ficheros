Servidor Ficheros 0.91
================================

Aplicación desarrollada en Java que implementa un servidor de transferencia de ficheros mediante Sockets.
El programa permite al cliente recibir ficheros enviados desde el servidor de forma concurrente, siendo
posible la conexión de un número indeterminado de éstos a un mismo servidor.

La aplicación habilita diferentes interfaces gráficas tanto para el cliente como para el servidor, posibilitando
la selección múltiple de varios ficheros a la vez, que serán alojados en el directorio 'src/recursos/tmp' y 
mostrados al cliente tras su posterior recepción.

Para facilitar la puesta en marcha de la aplicación se proporcionan varios ejecutables .jar con el 
proyecto construido y listo para ser ejecutado de manera gráfica.

## Requisitos
- [Java] 7 o superior (para ejecutar la Aplicación)

## Entorno de desarrollo
La Aplicación ha sido desarrollada utilizando el IDE [NetBeans] pero también es posible su importanción 
en [Eclipe] y demás IDE's.

## Licencia
Esta aplicación se ofrece bajo licencia [GPL versión 3].

[GPL versión 3]: https://www.gnu.org/licenses/gpl-3.0.en.html
[NetBeans]: https://netbeans.org/
[Eclipe]: https://eclipse.org/
[Java]: https://www.java.com/
