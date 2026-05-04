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

@CrossOrigin(origins="http://localhost:5173")
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

    @GetMapping("/{idDetalleE}")
    public ResponseEntity<DetalleEvaluacion> findById(@PathVariable Long idDetalleE) {
        Optional<DetalleEvaluacion> detalleOptional = detalleEvaluacionRepository.findById(idDetalleE);
        if (detalleOptional.isPresent()) {
            return ResponseEntity.ok(detalleOptional.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<DetalleEvaluacion> create(@RequestBody DetalleEvaluacion newDetalle, UriComponentsBuilder ucb) {
        Optional<Alumno> alumnoOptional = alumnoRepository.findById(newDetalle.getAlumno().getIdAlumno());
        Optional<Evaluacion> evaluacionOptional = evaluacionRepository.findById(newDetalle.getEvaluacion().getIdEvaluacion());
        if (!alumnoOptional.isPresent() || !evaluacionOptional.isPresent()) {
            return ResponseEntity.unprocessableEntity().build();
        }
        newDetalle.setAlumno(alumnoOptional.get());
        newDetalle.setEvaluacion(evaluacionOptional.get());
        DetalleEvaluacion savedDetalle = detalleEvaluacionRepository.save(newDetalle);
        URI uri = ucb
                .path("/detalleEvaluacion/{idDetalleE}")
                .buildAndExpand(savedDetalle.getIdDetalleE())
                .toUri();

        return ResponseEntity.created(uri).body(savedDetalle);
    }

    @PutMapping("/{idDetalleE}")
    public ResponseEntity<Void> update(@PathVariable Long idDetalleE, @RequestBody DetalleEvaluacion detalle){
        Optional<Alumno> alumnoOptional = alumnoRepository.findById(detalle.getAlumno().getIdAlumno());
        Optional<Evaluacion> evaluacionOptional = evaluacionRepository.findById(detalle.getEvaluacion().getIdEvaluacion());
        if(!alumnoOptional.isPresent()) {
            return ResponseEntity.unprocessableEntity().build();
        }
        DetalleEvaluacion detalleAnterior = detalleEvaluacionRepository.findById(idDetalleE).get();
        if(detalleAnterior != null) {
            detalle.setEvaluacion(evaluacionOptional.get());
            detalle.setAlumno(alumnoOptional.get());
            detalle.setIdDetalleE(detalleAnterior.getIdDetalleE());
            detalleEvaluacionRepository.save(detalle);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{idDetalleE}")
    public ResponseEntity<Void> delete(@PathVariable Long idDetalleE) {
        if (detalleEvaluacionRepository.findById(idDetalleE) != null) {
            detalleEvaluacionRepository.deleteById(idDetalleE);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/detalleEConsulta")
    public ResponseEntity<Iterable<QueryDetalleEvaluacion>> findDetalleEvaluacion(){
        return ResponseEntity.ok(detalleEvaluacionRepository.findDetalleEvaluacion());
    }
}
