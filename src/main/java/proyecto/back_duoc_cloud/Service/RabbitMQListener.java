/* 
 * Este archivo escucha mensajes en la cola y procesa las alertas. Su implementación actual cubre 
 * los siguientes casos:
 *      Crear nueva alerta (POST): Se guarda en la base de datos.
 *      Actualizar alerta existente (PUT): Se busca y actualiza la alerta en la base de datos.
 *      Eliminar alerta (DELETE): Se elimina la alerta por ID.
 */
package proyecto.back_duoc_cloud.Service;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
import proyecto.back_duoc_cloud.Config.RabbitMQConfig;
import proyecto.back_duoc_cloud.Model.AlertaMedica;
import proyecto.back_duoc_cloud.Repository.AlertaMedicaRepository;

@Service
public class RabbitMQListener {

    private final AlertaMedicaRepository alertaMedicaRepository;

    public RabbitMQListener(AlertaMedicaRepository alertaMedicaRepository) {
        this.alertaMedicaRepository = alertaMedicaRepository;
    }

    @RabbitListener(queues = RabbitMQConfig.QUEUE_ALERTAS)
    public void procesarMensaje(Object mensaje) {
        if (mensaje instanceof AlertaMedica alerta) {
            // Validar que sea una alerta grave antes de procesar
            if ("Alta".equalsIgnoreCase(alerta.getNivelAlerta())) {
                if (alerta.getIdAlerta() == null) {
                    // Procesar POST (Crear alerta grave)
                    System.out.println("Creando nueva alerta grave: " + alerta);
                    alertaMedicaRepository.save(alerta);
                } else {
                    // Procesar PUT (Actualizar alerta grave)
                    System.out.println("Actualizando alerta grave: " + alerta);
                    alertaMedicaRepository.findById(alerta.getIdAlerta()).ifPresent(existingAlerta -> {
                        existingAlerta.setNombrePaciente(alerta.getNombrePaciente());
                        existingAlerta.setTipoAlerta(alerta.getTipoAlerta());
                        existingAlerta.setNivelAlerta(alerta.getNivelAlerta());
                        existingAlerta.setFechaAlerta(alerta.getFechaAlerta());
                        alertaMedicaRepository.save(existingAlerta);
                    });
                }
            } else {
                // Si no es una alerta grave, no procesarla
                System.out.println("Mensaje descartado (no es una alerta grave): " + alerta);
            }
        } else if (mensaje instanceof Long idAlerta) {
            // Procesar DELETE (Eliminar alerta grave)
            System.out.println("Eliminando alerta grave con ID: " + idAlerta);
            alertaMedicaRepository.deleteById(idAlerta);
        } else {
            System.err.println("Tipo de mensaje no reconocido: " + mensaje);
        }
    }
}