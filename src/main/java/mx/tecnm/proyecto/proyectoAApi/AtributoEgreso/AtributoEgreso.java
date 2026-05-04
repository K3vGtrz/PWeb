package mx.tecnm.proyecto.proyectoAApi.AtributoEgreso;

import jakarta.persistence.*;
import lombok.*;
import mx.tecnm.proyecto.proyectoAApi.AtributoEvaluar.AtributoEvaluar;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
@Entity
@Table(name = "atributoEgreso")
public class AtributoEgreso {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idAtributo;
    @Column(nullable = false, length = 80)
    private String tipo_Atributo;
    @Column(nullable = false, length = 200)
    private String descripcion;
    @Column(nullable = false)
    private Float ponderacion;

    @OneToMany(mappedBy = "atributoEgreso", cascade = CascadeType.ALL)
    private List<AtributoEvaluar> atributoEvaluar = new ArrayList<>();
}
