package mx.tecnm.proyecto.proyectoAApi.Evaluacion;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EvaluacionRepository extends CrudRepository<Evaluacion, Long> {
    @Query("SELECT e.calificacion AS calificacion, " +
            "e.fecha AS fecha, " +
            "eval.nombre_Evaluador AS nombreEvaluador, " +
            "eval.materia AS materia " +
            "FROM evaluacion e " +
            "JOIN Evaluador eval ON e.evaluador.idEvaluador = eval.idEvaluador")
    List<QueryEvaluacion> findEvaluacion();
}
