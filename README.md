# Back-Duoc-Cloud 🚀
Backend desarrollado en **Spring Boot 3.4.1**, con integración a **RabbitMQ** y **Oracle Cloud**.

## 📌 Características
- 🏥 **Manejo de alertas médicas** (envío de alertas a RabbitMQ)
- ☁ **Base de datos en Oracle Cloud** (opcional)
- 🐇 **Mensajería con RabbitMQ** (conexión entre microservicios)
- 🐳 **Contenedores con Docker y Docker Compose**
- 🔒 **Seguridad con Spring Security**

---

## 🛠️ **Configuración y Ejecución**

### 📦 **Requisitos**
Asegúrate de tener instalado:
- [Docker](https://www.docker.com/)
- [Docker Compose](https://docs.docker.com/compose/)
- [Java 17](https://adoptium.net/)
- [Maven](https://maven.apache.org/)

### 🚀 **Ejecutar con Docker**

# ➕ Compilar el proyecto
mvn clean package -DskipTests

# 🛠️ Construir las imágenes de Docker
docker-compose build --no-cache

# 🔄 Levantar los contenedores
docker-compose up

Esto iniciará:
- 🏥 **back-duoc-cloud** (API en Spring Boot)
- 🐇 **RabbitMQ** (Mensajería)
- ☁ **Oracle Cloud (si está configurado)**

---

## 🐇 **Configuración de RabbitMQ**
RabbitMQ está configurado en `application.properties`:

properties
spring.rabbitmq.host=rabbitmq
spring.rabbitmq.port=5672
spring.rabbitmq.username=guest
spring.rabbitmq.password=guest


Verifica el panel de administración en:  
🔗 **[http://localhost:15672](http://localhost:15672)**  
Usuario: `guest`  
Contraseña: `guest`

---

## ☁ **Conexión a Oracle Cloud**
La base de datos Oracle Cloud **es opcional**. Si quieres usarla, asegúrate de que en `application.properties` esté configurado:

properties
spring.datasource.url=jdbc:oracle:thin:@backduoccloud_high?TNS_ADMIN=/app/wallet
spring.datasource.username=ADMIN
spring.datasource.password=Secure1Password2Example
spring.datasource.driver-class-name=oracle.jdbc.OracleDriver


---

## 🔄 **Cómo Enviar y Recibir Alertas**
### 📩 **Enviar una alerta (POST)**

curl -X POST http://localhost:8080/api/alertas \
     -H "Content-Type: application/json" \
     -d '{
           "nombrePaciente": "Juan Pérez",
           "tipoAlerta": "Cardiaca",
           "nivelAlerta": "Alta"
    }'

Esto enviará la alerta a RabbitMQ.

### 📥 **Recibir alertas desde RabbitMQ**
Las alertas enviadas se pueden ver en la interfaz de administración de RabbitMQ en la cola `queues_alertasmedicas`.
