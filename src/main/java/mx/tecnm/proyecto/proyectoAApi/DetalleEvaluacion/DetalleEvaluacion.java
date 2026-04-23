package mx.tecnm.proyecto.proyectoAApi.DetalleEvaluacion;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
    private Long id_DetalleEvaluacion;
    @Column(nullable = false)
    private Float puntaje;
    @Column(nullable = false, length = 60)
    private String status;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_Evaluacion")
    @JsonIgnoreProperties({"detalleEvaluacion", "evaluador"})
    private Evaluacion evaluacion;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "numControl")
    @JsonIgnoreProperties("detalleEvaluacion")
    @JsonBackReference
    private Alumno alumno;
}
