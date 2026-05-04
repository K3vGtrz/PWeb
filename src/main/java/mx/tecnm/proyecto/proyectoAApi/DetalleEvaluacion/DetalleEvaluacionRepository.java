package mx.tecnm.proyecto.proyectoAApi.DetalleEvaluacion;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DetalleEvaluacionRepository extends CrudRepository<DetalleEvaluacion, Long> {
    @Query("SELECT de.puntaje AS puntaje, " +
            "de.status AS status, " +
            "e.fecha AS fecha, " +
            "a.numControl AS numControl, " +
            "a.nombre AS nombre, " +
            "a.semestre AS semestre, " +
            "a.grupo AS grupo " +
            "FROM detalleEvaluacion de " +
            "LEFT JOIN Evaluacion e ON de.evaluacion.idEvaluacion = e.idEvaluacion " +
            "LEFT JOIN Alumno a ON a.alumno.idAlumno = a.idAlumno")
    List<QueryDetalleEvaluacion> findDetalleEvaluacion();
}
