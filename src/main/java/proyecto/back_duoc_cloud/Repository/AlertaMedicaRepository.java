package proyecto.back_duoc_cloud.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import proyecto.back_duoc_cloud.Model.AlertaMedica;;

public interface AlertaMedicaRepository extends JpaRepository<AlertaMedica, Long> {
}