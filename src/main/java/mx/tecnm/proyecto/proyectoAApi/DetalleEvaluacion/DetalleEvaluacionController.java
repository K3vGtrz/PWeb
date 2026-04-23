package mx.tecnm.proyecto.proyectoAApi.DetalleEvaluacion;

import mx.tecnm.proyecto.proyectoAApi.Alumno.Alumno;
import mx.tecnm.proyecto.proyectoAApi.Alumno.AlumnoRepository;
import mx.tecnm.proyecto.proyectoAApi.Evaluacion.Evaluacion;
import mx.tecnm.proyecto.proyectoAApi.Evaluacion.EvaluacionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Optional;

@RestController
@RequestMapping("detalleEvaluacion")
public class DetalleEvaluacionController {
    @Autowired
    private DetalleEvaluacionRepository detalleEvaluacionRepository;
    @Autowired
    private AlumnoRepository alumnoRepository;
    @Autowired
    private EvaluacionRepository evaluacionRepository;


    @GetMapping
    public ResponseEntity<Iterable<DetalleEvaluacion>> findAll(){
        return ResponseEntity.ok(detalleEvaluacionRepository.findAll());
    }

    @GetMapping("/{id_DetalleEvaluacion}")
    public ResponseEntity<DetalleEvaluacion> findById(@PathVariable Long id_DetalleEvaluacion) {
        Optional<DetalleEvaluacion> detalleOptional = detalleEvaluacionRepository.findById(id_DetalleEvaluacion);
        if (detalleOptional.isPresent()) {
            return ResponseEntity.ok(detalleOptional.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<DetalleEvaluacion> create(@RequestBody DetalleEvaluacion newDetalle, UriComponentsBuilder ucb) {
        Optional<Alumno> alumnoOptional = alumnoRepository.findById(newDetalle.getAlumno().getNumControl());
        Optional<Evaluacion> evaluacionOptional = evaluacionRepository.findById(newDetalle.getEvaluacion().getId_Evaluacion());
        if (!alumnoOptional.isPresent() || !evaluacionOptional.isPresent()) {
            return ResponseEntity.unprocessableEntity().build();
        }
        newDetalle.setAlumno(alumnoOptional.get());
        newDetalle.setEvaluacion(evaluacionOptional.get());
        DetalleEvaluacion savedDetalle = detalleEvaluacionRepository.save(newDetalle);
        URI uri = ucb
                .path("/detalleEvaluacion/{id_DetalleEvaluacion}")
                .buildAndExpand(savedDetalle.getId_DetalleEvaluacion())
                .toUri();

        return ResponseEntity.created(uri).body(savedDetalle);
    }

    @PutMapping("/{id_DetalleEvaluacion}")
    public ResponseEntity<Void> update(@PathVariable Long id_DetalleEvaluacion, @RequestBody DetalleEvaluacion detalle){
        Optional<Alumno> alumnoOptional = alumnoRepository.findById(detalle.getAlumno().getNumControl());
        Optional<Evaluacion> evaluacionOptional = evaluacionRepository.findById(detalle.getEvaluacion().getId_Evaluacion());
        if(!alumnoOptional.isPresent()) {
            return ResponseEntity.unprocessableEntity().build();
        }
        DetalleEvaluacion detalleAnterior = detalleEvaluacionRepository.findById(id_DetalleEvaluacion).get();
        if(detalleAnterior != null) {
            detalle.setEvaluacion(evaluacionOptional.get());
            detalle.setAlumno(alumnoOptional.get());
            detalle.setId_DetalleEvaluacion(detalleAnterior.getId_DetalleEvaluacion());
            detalleEvaluacionRepository.save(detalle);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id_DetalleEvaluacion}")
    public ResponseEntity<Void> delete(@PathVariable Long id_DetalleEvaluacion) {
        if (detalleEvaluacionRepository.findById(id_DetalleEvaluacion) != null) {
            detalleEvaluacionRepository.deleteById(id_DetalleEvaluacion);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
    @ExceptionHandler(org.springframework.http.converter.HttpMessageNotReadableException.class)
    public ResponseEntity<String> handleJsonErrors(Exception e) {
        return ResponseEntity.badRequest().body("Error en el formato JSON: " + e.getMessage());
    }
}
