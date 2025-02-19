#!/bin/bash

# 1. Clonar los repositorios
echo "Clonando repositorios..."
git clone https://github.com/pabloperezarcos/back-duoc-cloud.git
git clone https://github.com/pabloperezarcos/back_duoc_consumer.git
git clone https://github.com/pabloperezarcos/back-duoc-json-writer.git

# 2. Construir las imágenes de Docker
echo "Construyendo imágenes..."
cd back-duoc-cloud && docker build -t back-duoc-cloud . && cd ..
cd back_duoc_consumer && docker build -t back_duoc_consumer . && cd ..
cd back-duoc-json-writer && docker build -t back-duoc-json-writer . && cd ..

# 3. Ejecutar los contenedores
echo "Ejecutando contenedores..."
docker network create sumativa_duoc 2>/dev/null || echo "Red sumativa_duoc ya existe"

docker run -d --name rabbitmq --network sumativa_duoc -p 5672:5672 -p 15672:15672 rabbitmq:management

docker run -d --name back-duoc-cloud --network sumativa_duoc -p 8080:8080 back-duoc-cloud
docker run -d --name back_duoc_consumer --network sumativa_duoc -p 8081:8080 back_duoc_consumer
docker run -d --name back-duoc-json-writer --network sumativa_duoc -p 8082:8080 back-duoc-json-writer

echo "Todos los servicios han sido iniciados correctamente."
echo "Puedes acceder a RabbitMQ en http://localhost:15672 (usuario: guest, contraseña: guest)"
