package mx.tecnm.proyecto.proyectoAApi.DetalleEvaluacion;

import java.time.LocalDate;

public interface QueryDetalleEvaluacion {
    Float getPuntaje();
    String getStatus();
    LocalDate getFecha();
    String getNumControl();
    String getNombre();
    Short getSemestre();
    Character getGrupo();
}
