package proyecto.back_duoc_cloud.Model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Data; //Con Lombok no es necesario escribir los Get y Set

import java.time.Instant;
//import java.time.LocalDateTime;

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
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", timezone = "UTC")
    private Instant fechaAlerta = Instant.now();
}
