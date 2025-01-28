package proyecto.back_duoc_cloud.Controller;

import proyecto.back_duoc_cloud.Config.RabbitMQConfig;
import proyecto.back_duoc_cloud.Model.AlertaMedica;
import proyecto.back_duoc_cloud.Service.AlertaMedicaService;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public ResponseEntity<String> crearAlerta(@RequestBody AlertaMedica alerta) {
        // Guardar siempre en la base de datos
        service.guardarAlerta(alerta);

        // Verificar si la alerta es "grave" (nivelAlerta = "Alta") y enviarla a
        // RabbitMQ
        if ("Alta".equalsIgnoreCase(alerta.getNivelAlerta())) {
            rabbitTemplate.convertAndSend(
                    RabbitMQConfig.EXCHANGE_ALERTAS,
                    RabbitMQConfig.ROUTING_KEY,
                    alerta);
            return ResponseEntity.ok("Alerta guardada y enviada a RabbitMQ por ser de nivel 'Alta'.");
        }

        return ResponseEntity.ok("Alerta guardada en la base de datos.");
    }

    // PUT: Actualizar una alerta existente
    @PutMapping("/{id}")
    public ResponseEntity<String> actualizarAlerta(@PathVariable Long id, @RequestBody AlertaMedica alerta) {
        alerta.setIdAlerta(id);
        service.actualizarAlerta(id, alerta);

        // Verificar si la alerta actualizada es "grave" (nivelAlerta = "Alta") y
        // enviarla a RabbitMQ
        if ("Alta".equalsIgnoreCase(alerta.getNivelAlerta())) {
            rabbitTemplate.convertAndSend(
                    RabbitMQConfig.EXCHANGE_ALERTAS,
                    RabbitMQConfig.ROUTING_KEY,
                    alerta);
            return ResponseEntity.ok("Alerta actualizada y enviada a RabbitMQ por ser de nivel 'Alta'.");
        }

        return ResponseEntity.ok("Alerta actualizada en la base de datos.");
    }

    // DELETE: Eliminar una alerta por ID
    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarAlerta(@PathVariable Long id) {
        service.eliminarAlerta(id);
        return ResponseEntity.ok("Alerta eliminada de la base de datos.");
    }

}