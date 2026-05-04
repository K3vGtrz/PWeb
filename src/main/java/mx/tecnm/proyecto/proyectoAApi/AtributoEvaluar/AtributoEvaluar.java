package mx.tecnm.proyecto.proyectoAApi.AtributoEvaluar;

import com.fasterxml.jackson.annotation.JsonProperty;
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
    private Long idAevaluar;
    @Column(nullable = false,  length = 90)
    private String atributoAplicable;

    @ManyToOne
    @JoinColumn(name = "idEvaluacion")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Evaluacion evaluacion;

    @ManyToOne
    @JoinColumn(name = "idAtributo")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private AtributoEgreso atributoEgreso;
}
