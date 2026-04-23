package mx.tecnm.proyecto.proyectoAApi.AtributoEgreso;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;
import mx.tecnm.proyecto.proyectoAApi.AtributoEvaluar.AtributoEvaluar;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
@Entity
@Table(name = "atributoEgreso")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class AtributoEgreso {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_Atributo;
    @Column(nullable = false, length = 80)
    private String tipo_Atributo;
    @Column(nullable = false, length = 200)
    private String descripcion;
    @Column(nullable = false)
    private Float ponderacion;

    @OneToMany(mappedBy = "atributoEgreso")
    private List<AtributoEvaluar> atributoEvaluar;
}
