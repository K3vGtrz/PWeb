package mx.tecnm.proyecto.proyectoAApi.DetalleEvaluacion;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;
import mx.tecnm.proyecto.proyectoAApi.Alumno.Alumno;
import mx.tecnm.proyecto.proyectoAApi.Evaluacion.Evaluacion;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
@Entity
@Table(name = "detalleEvaluacion")
public class DetalleEvaluacion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idDetalleE;
    @Column(nullable = false)
    private Float puntaje;
    @Column(nullable = false, length = 60)
    private String status;

    @ManyToOne
    @JoinColumn(name = "idEvaluacion")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Evaluacion evaluacion;

    @ManyToOne
    @JoinColumn(name = "idAlumno")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Alumno alumno;
}
