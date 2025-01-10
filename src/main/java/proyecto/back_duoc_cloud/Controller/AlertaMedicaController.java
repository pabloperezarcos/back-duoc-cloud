package proyecto.back_duoc_cloud.Controller;

import proyecto.back_duoc_cloud.Model.AlertaMedica;
import proyecto.back_duoc_cloud.Service.AlertaMedicaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/alertas")
public class AlertaMedicaController {
    private final AlertaMedicaService service;

    public AlertaMedicaController(AlertaMedicaService service) {
        this.service = service;
    }

    @GetMapping
    public List<AlertaMedica> obtenerAlertas() {
        return service.obtenerAlertas();
    }

    @PostMapping
    public ResponseEntity<AlertaMedica> crearAlerta(@RequestBody AlertaMedica alerta) {
        return ResponseEntity.ok(service.guardarAlerta(alerta));
    }

    @PutMapping("/{id}")
    public ResponseEntity<AlertaMedica> actualizarAlerta(@PathVariable Long id, @RequestBody AlertaMedica alerta) {
        return ResponseEntity.ok(service.actualizarAlerta(id, alerta));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarAlerta(@PathVariable Long id) {
        service.eliminarAlerta(id);
        return ResponseEntity.noContent().build();
    }
}