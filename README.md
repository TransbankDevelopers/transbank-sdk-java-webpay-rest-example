Proyecto de ejemplo para uso del SDK Rest de Transbank para Java
--

El siguiente proyecto es una simulacion de un ecommerce el cual utiliza los distintos servicios de webpay rest a traves del SDK de Transbank para java


## Requerimientos
Para ejecutar el proyecto es necesario tener: 
 ```docker``` y ```docker-compose``` ([como instalar Docker](https://docs.docker.com/install/))

## Clonar o bajar proyecto desde github

([transbank-sdk-java-webpay-rest-example-github](https://github.com/TransbankDevelopers/transbank-sdk-java-webpay-rest-example))

## Ejecutar ejemplo
Con el código fuente del proyecto en tu computador, puedes ejecutar en la raíz del proyecto el comando para construir el contenedor docker, si es la primera vez que ejecutas el proyecto:
```bash
docker-compose build
```
Luego, es necesario instalar las dependencias:
```bash
docker-compose run web composer install
```
Finalmente, para correr el proyecto de ejemplo:
```
docker-compose run --service-ports web php artisan serve --host=0.0.0.0 --port=8000
```
También puedes iniciar el proyecto simplemente ejecutando el archivo `run.sh` en la raíz del proyecto

En ambos casos el proyecto se ejecutará en http://localhost:8000 (y fallará en caso de que el puerto 8000 no esté disponible)

Puedes mirar el SDK de Java aqui [SDK Rest Java](https://github.com/TransbankDevelopers/transbank-sdk-java)


## Index

![index](img/index.png)

## Webpay Plus

![webpay-plus](img/webpayplus.png)

## Webpay Plus Mall

![webpay-plus-mall](img/webpayplus-mall.png)

## Webpay Plus Captura Diferida

![webpay-plus-captura-diferida](img/webpayplus-deferred.png)

## Webpay Plus Mall Captura Diferida

![webpay-plus-mall-captura diferida](img/webpayplus-mall-deferred.png)

## Oneclick Mall

![Oneclick-mall](img/oneclick-mall.png)

## Transaccion Completa

![transaccion-completa](img/fulltransaction.png)

## Patpass Webpay

![patpass-webpay](img/patpass-webpay.png)

## Transaccion Completa Mall

![transaccion-completa-mall](img/fulltransaction-mall.png)

## Patpass Comercio

![patpass-comercio](img/patpass-comercio.png)


