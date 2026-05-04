package mx.tecnm.proyecto.proyectoAApi.Evaluacion;

import mx.tecnm.proyecto.proyectoAApi.Evaluador.Evaluador;
import mx.tecnm.proyecto.proyectoAApi.Evaluador.EvaluadorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Optional;

@CrossOrigin(origins="http://localhost:5173")
@RestController
@RequestMapping("/evaluacion")
public class EvaluacionController {
    @Autowired
    private EvaluacionRepository evaluacionRepository;
    @Autowired
    private EvaluadorRepository evaluadorRepository;

    @GetMapping()
    public ResponseEntity<Iterable<Evaluacion>> findALl() {
        return ResponseEntity.ok(evaluacionRepository.findAll());
    }

    @GetMapping("/{idEvaluacion}")
    public ResponseEntity<Evaluacion> findById(@PathVariable Long idEvaluacion) {
        Optional<Evaluacion> evaluacionOptional = evaluacionRepository.findById(idEvaluacion);
        if (evaluacionOptional.isPresent()) {
            return ResponseEntity.ok(evaluacionOptional.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<Evaluacion> create(@RequestBody Evaluacion evaluacion, UriComponentsBuilder uriBuilder) {
        Optional<Evaluador> evaluadorOptional = evaluadorRepository.findById(evaluacion.getEvaluador().getIdEvaluador());
        if (!evaluadorOptional.isPresent()) {
            return ResponseEntity.unprocessableEntity().build();
        }
        evaluacion.setEvaluador(evaluadorOptional.get());
        Evaluacion created = evaluacionRepository.save(evaluacion);
        URI uri = uriBuilder.path("/evaluacion/{idEvaluacion}").buildAndExpand(created.getIdEvaluacion()).toUri();
        return ResponseEntity.created(uri).body(created);
    }

    @PutMapping("/{idEvaluacion}")
    public ResponseEntity<Void> update(@PathVariable Long idEvaluacion, @RequestBody Evaluacion evalucion) {
        Optional<Evaluador> evaluadorOptional = evaluadorRepository.findById(evalucion.getEvaluador().getIdEvaluador());
        if (!evaluadorOptional.isPresent()) {
            return ResponseEntity.unprocessableEntity().build();
        }
        Evaluacion evaluacionAnterior = evaluacionRepository.findById(idEvaluacion).get();
        if (evaluacionAnterior != null) {
            evalucion.setEvaluador(evaluadorOptional.get());
            evalucion.setIdEvaluacion(evaluacionAnterior.getIdEvaluacion());
            evaluacionRepository.save(evalucion);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
    @DeleteMapping("/{idEvaluacion}")
    public ResponseEntity<Void> delete(@PathVariable Long idEvaluacion) {
        if (evaluacionRepository.findById(idEvaluacion).isPresent()) {
            evaluacionRepository.deleteById(idEvaluacion);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
