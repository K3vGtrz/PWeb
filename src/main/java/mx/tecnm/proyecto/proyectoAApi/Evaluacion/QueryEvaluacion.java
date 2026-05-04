package mx.tecnm.proyecto.proyectoAApi.Evaluacion;

import java.time.LocalDate;

public interface QueryEvaluacion {
    Float getCalificacion();
    LocalDate getFecha();
    String getNombre_Evaluador();
    String getMateria();
}
