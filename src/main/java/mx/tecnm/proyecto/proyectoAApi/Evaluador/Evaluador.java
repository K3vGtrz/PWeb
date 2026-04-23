package mx.tecnm.proyecto.proyectoAApi.Evaluador;

import jakarta.persistence.*;
import lombok.*;
import mx.tecnm.proyecto.proyectoAApi.Evaluacion.Evaluacion;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
@Entity
@Table(name="evaluador")
public class Evaluador {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_Evaluador;
    @Column(nullable = false, length = 100)
    private String nombre_Evaluador;
    @Column(nullable = false, length = 50)
    private String materia;

    @OneToMany(mappedBy = "evaluador")
    private List<Evaluacion> evaluacion;
}
