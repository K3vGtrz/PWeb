package mx.tecnm.proyecto.proyectoAApi.AtributoEvaluar;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AtributoEvaluarRepository extends CrudRepository<AtributoEvaluar, Long> {
    @Query("SELECT eval.atributoAplicable AS atributoAplicable, "+
            "egr.descripcion AS descripcion, "+
            "egr.ponderacion AS ponderacion, "+
             "e.calificacion AS calificacion, "+
            "e.fecha AS fecha " +
            "FROM atributoEvaluar eval"+
            "LEFT JOIN AtributoEgreso egr ON eval.atributo.idAtributo = egr.idAtributo"+
            "LEFT JOIN Evaluacion e ON eval.evaluacion.idEvaluacion  =e.idEvaluacion")
    List<QueryAtributoEvaluar> findAtrEvaluar();
}
