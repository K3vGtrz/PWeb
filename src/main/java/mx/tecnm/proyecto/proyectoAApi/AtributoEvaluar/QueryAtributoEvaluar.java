package mx.tecnm.proyecto.proyectoAApi.AtributoEvaluar;

import java.time.LocalDate;

public interface QueryAtributoEvaluar {
    String getAtributoAplicable();
    String getDescripcion();
    Float getPonderacion();
    Float getCalificacion();
    LocalDate getFecha();
}
