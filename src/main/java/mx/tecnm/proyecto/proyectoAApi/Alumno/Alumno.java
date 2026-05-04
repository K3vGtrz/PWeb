package mx.tecnm.proyecto.proyectoAApi.Alumno;

import jakarta.persistence.*;
import lombok.*;
import mx.tecnm.proyecto.proyectoAApi.DetalleEvaluacion.DetalleEvaluacion;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
@Entity
@Table(name = "alumno")
public class Alumno {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idAlumno;
    @Column(nullable = false, length = 8)
    private String numControl;
    @Column(nullable = false, length = 60)
    private String nombre;
    @Column(nullable = false)
    private Short semestre;
    @Column(nullable = false)
    private Character grupo;

    @OneToMany(mappedBy = "alumno", cascade = CascadeType.ALL)
    private List<DetalleEvaluacion> detalleEvaluacion = new ArrayList<>();
}
