package proyecto.back_duoc_cloud.Service;

import java.util.List;

import proyecto.back_duoc_cloud.Model.AlertaMedica;

public interface AlertaMedicaService {
    List<AlertaMedica> obtenerAlertas();

    AlertaMedica guardarAlerta(AlertaMedica alerta);

    AlertaMedica actualizarAlerta(Long id, AlertaMedica alertaActualizada);

    void eliminarAlerta(Long id);
}