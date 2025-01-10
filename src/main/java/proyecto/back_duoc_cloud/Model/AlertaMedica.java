package proyecto.back_duoc_cloud.Model;

import jakarta.persistence.*;
import lombok.Data; //Con Lombok no es necesario escribir los Get y Set
import java.time.LocalDateTime;

@Entity
@Table(name = "alertas_medicas")
@Data // Esta anotación genera automáticamente los Getters and Setters.
public class AlertaMedica {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idAlerta;

    @Column(nullable = false)
    private String nombrePaciente;

    @Column(nullable = false)
    private String tipoAlerta;

    @Column(nullable = false)
    private String nivelAlerta;

    @Column(nullable = false)
    private LocalDateTime fechaAlerta = LocalDateTime.now();
}

