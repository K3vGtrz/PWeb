package mx.tecnm.proyecto.proyectoAApi.Evaluacion;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;
import mx.tecnm.proyecto.proyectoAApi.DetalleEvaluacion.DetalleEvaluacion;
import mx.tecnm.proyecto.proyectoAApi.Evaluador.Evaluador;

import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
@Entity
@Table(name="evaluacion")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Evaluacion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_Evaluacion;
    @Column(nullable = false)
    private Float calificacion;
    @Column(nullable = false, updatable = false)
    private LocalDate fecha;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_Evaluador")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Evaluador evaluador;

    @OneToMany(mappedBy = "evaluacion", cascade = CascadeType.ALL)
    private List<DetalleEvaluacion> detalleEvaluacion;

    @PrePersist
    protected void onCreate() {
        this.fecha = LocalDate.now();
    }
}
