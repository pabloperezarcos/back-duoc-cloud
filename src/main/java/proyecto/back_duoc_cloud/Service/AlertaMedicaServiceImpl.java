package proyecto.back_duoc_cloud.Service;

import proyecto.back_duoc_cloud.Model.AlertaMedica;
import proyecto.back_duoc_cloud.Repository.AlertaMedicaRepository;
//import proyecto.back_duoc_cloud.Service.AlertaMedicaService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AlertaMedicaServiceImpl implements AlertaMedicaService {
    private final AlertaMedicaRepository repository;

    public AlertaMedicaServiceImpl(AlertaMedicaRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<AlertaMedica> obtenerAlertas() {
        return repository.findAll();
    }

    @Override
    public AlertaMedica guardarAlerta(AlertaMedica alerta) {
        return repository.save(alerta);
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
