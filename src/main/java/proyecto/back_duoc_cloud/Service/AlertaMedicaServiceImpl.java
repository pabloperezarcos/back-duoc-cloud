package proyecto.back_duoc_cloud.Service;

import proyecto.back_duoc_cloud.Config.RabbitMQConfig;
import proyecto.back_duoc_cloud.Model.AlertaMedica;
import proyecto.back_duoc_cloud.Repository.AlertaMedicaRepository;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AlertaMedicaServiceImpl implements AlertaMedicaService {

    private final AlertaMedicaRepository repository;
    private final RabbitTemplate rabbitTemplate;

    public AlertaMedicaServiceImpl(AlertaMedicaRepository repository, RabbitTemplate rabbitTemplate) {
        this.repository = repository;
        this.rabbitTemplate = rabbitTemplate;
    }

    @Override
    public List<AlertaMedica> obtenerAlertas() {
        return repository.findAll();
    }

    @Override
    public AlertaMedica guardarAlerta(AlertaMedica alerta) {
        // Guardar alerta en la base de datos
        AlertaMedica alertaGuardada = repository.save(alerta);

        // Enviar a RabbitMQ si es una alerta grave
        if ("Alta".equalsIgnoreCase(alerta.getNivelAlerta())) {
            rabbitTemplate.convertAndSend(
                    RabbitMQConfig.EXCHANGE_ALERTAS,
                    RabbitMQConfig.ROUTING_KEY,
                    alertaGuardada);
            System.out.println("Alerta grave enviada a RabbitMQ: " + alertaGuardada);
        }

        return alertaGuardada;
    }

    @Override
    public AlertaMedica actualizarAlerta(Long id, AlertaMedica alertaActualizada) {
        return repository.findById(id)
                .map(alerta -> {
                    alerta.setNombrePaciente(alertaActualizada.getNombrePaciente());
                    alerta.setTipoAlerta(alertaActualizada.getTipoAlerta());
                    alerta.setNivelAlerta(alertaActualizada.getNivelAlerta());
                    alerta.setFechaAlerta(alertaActualizada.getFechaAlerta());
                    return repository.save(alerta);
                })
                .orElseThrow(() -> new RuntimeException("Alerta no encontrada con ID: " + id));
    }

    @Override
    public void eliminarAlerta(Long id) {
        repository.deleteById(id);
    }
}
