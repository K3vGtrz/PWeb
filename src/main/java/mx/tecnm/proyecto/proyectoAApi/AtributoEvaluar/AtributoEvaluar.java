package mx.tecnm.proyecto.proyectoAApi.AtributoEvaluar;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;
import mx.tecnm.proyecto.proyectoAApi.AtributoEgreso.AtributoEgreso;
import mx.tecnm.proyecto.proyectoAApi.Evaluacion.Evaluacion;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
@Entity
@Table(name = "atributoEvaluar")
public class AtributoEvaluar {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_AtributoEvaluar;
    @Column(nullable = false,  length = 90)
    private String atributoAplicable;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_Evaluacion")
    @JsonIgnoreProperties({"atributoEvaluar", "detalleEvaluacion", "evaluador"}) // Evita que la evaluación traiga sus hijos
    private Evaluacion evaluacion;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_AtributoEgreso")
    @JsonIgnoreProperties("atributoEvaluar") // Evita que el egreso traiga su lista de vuelta
    private AtributoEgreso atributoEgreso;
}
