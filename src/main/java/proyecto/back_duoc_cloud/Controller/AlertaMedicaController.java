package proyecto.back_duoc_cloud.Controller;

import proyecto.back_duoc_cloud.Config.RabbitMQConfig;
import proyecto.back_duoc_cloud.Model.AlertaMedica;
import proyecto.back_duoc_cloud.Service.AlertaMedicaService;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/alertas")
// Permite solicitudes desde el frontend
// @CrossOrigin(origins = "http://localhost:4200")
// @CrossOrigin(origins = "https://www.carnesag.cl")
@CrossOrigin(origins = "*")
public class AlertaMedicaController {

    private final RabbitTemplate rabbitTemplate;
    private final AlertaMedicaService service;

    public AlertaMedicaController(RabbitTemplate rabbitTemplate, AlertaMedicaService service) {
        this.rabbitTemplate = rabbitTemplate;
        this.service = service;
    }

    // GET: Obtener todas las alertas
    @GetMapping
    public List<AlertaMedica> obtenerAlertas() {
        return service.obtenerAlertas();
    }

    // POST: Crear una nueva alerta
    @PostMapping
    public ResponseEntity<Map<String, String>> crearAlerta(@RequestBody AlertaMedica alerta) {
        System.out.println("Nivel de alerta recibida: " + alerta.getNivelAlerta());

        // Guardar siempre en la base de datos
        // service.guardarAlerta(alerta);

        // Enviar la alerta a la cola adecuada según su nivel
        if ("Alta".equalsIgnoreCase(alerta.getNivelAlerta())) {
            // Enviar a la cola de alertas graves
            rabbitTemplate.convertAndSend(
                    RabbitMQConfig.EXCHANGE_ALERTAS_GRAVES,
                    RabbitMQConfig.ROUTING_KEY_GRAVES,
                    alerta);
            return ResponseEntity.ok(Collections.singletonMap(
                    "message", "Alerta guardada y enviada a la cola de alertas graves."));
        } else {
            // Enviar a la cola general de alertas
            rabbitTemplate.convertAndSend(
                    RabbitMQConfig.EXCHANGE_ALERTAS,
                    RabbitMQConfig.ROUTING_KEY,
                    alerta);
            return ResponseEntity.ok(Collections.singletonMap(
                    "message", "Alerta guardada y enviada a la cola general."));
        }
    }

    // PUT: Actualizar una alerta existente
    @PutMapping("/{id}")
    public ResponseEntity<Map<String, String>> actualizarAlerta(@PathVariable Long id,
            @RequestBody AlertaMedica alerta) {
        alerta.setIdAlerta(id);
        service.actualizarAlerta(id, alerta);

        // Actualizar la alerta en la base de datos y reenviarla según el nivel
        if ("Alta".equalsIgnoreCase(alerta.getNivelAlerta())) {
            rabbitTemplate.convertAndSend(
                    RabbitMQConfig.EXCHANGE_ALERTAS_GRAVES,
                    RabbitMQConfig.ROUTING_KEY_GRAVES,
                    alerta);
            return ResponseEntity.ok(Collections.singletonMap(
                    "message", "Alerta actualizada y reenviada a la cola de alertas graves."));
        } else {
            rabbitTemplate.convertAndSend(
                    RabbitMQConfig.EXCHANGE_ALERTAS,
                    RabbitMQConfig.ROUTING_KEY,
                    alerta);
            return ResponseEntity.ok(Collections.singletonMap(
                    "message", "Alerta actualizada y reenviada a la cola general."));
        }
    }

    // DELETE: Eliminar una alerta por ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> eliminarAlerta(@PathVariable Long id) {
        service.eliminarAlerta(id);
        return ResponseEntity.ok(Collections.singletonMap(
                "message", "Alerta eliminada de la base de datos."));
    }

}