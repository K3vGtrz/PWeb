package mx.tecnm.proyecto.proyectoAApi.Evaluacion;

import mx.tecnm.proyecto.proyectoAApi.Evaluador.Evaluador;
import mx.tecnm.proyecto.proyectoAApi.Evaluador.EvaluadorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Optional;

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

    @GetMapping("/{id_Evaluacion}")
    public ResponseEntity<Evaluacion> findById(@PathVariable Long id_Evaluacion) {
        Optional<Evaluacion> evaluacionOptional = evaluacionRepository.findById(id_Evaluacion);
        if (evaluacionOptional.isPresent()) {
            return ResponseEntity.ok(evaluacionOptional.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<Evaluacion> create(@RequestBody Evaluacion evaluacion, UriComponentsBuilder uriBuilder) {
        Optional<Evaluador> evaluadorOptional = evaluadorRepository.findById(evaluacion.getEvaluador().getId_Evaluador());
        if (!evaluadorOptional.isPresent()) {
            return ResponseEntity.unprocessableEntity().build();
        }
        evaluacion.setEvaluador(evaluadorOptional.get());
        Evaluacion created = evaluacionRepository.save(evaluacion);
        URI uri = uriBuilder.path("/evaluacion/{id_Evaluacion}").buildAndExpand(created.getId_Evaluacion()).toUri();
        return ResponseEntity.created(uri).body(created);
    }

    @PutMapping("/{id_Evaluacion}")
    public ResponseEntity<Void> update(@PathVariable Long id_Evaluacion, @RequestBody Evaluacion evalucion) {
        Optional<Evaluador> evaluadorOptional = evaluadorRepository.findById(evalucion.getEvaluador().getId_Evaluador());
        if (!evaluadorOptional.isPresent()) {
            return ResponseEntity.unprocessableEntity().build();
        }
        Evaluacion evaluacionAnterior = evaluacionRepository.findById(id_Evaluacion).get();
        if (evaluacionAnterior != null) {
            evalucion.setEvaluador(evaluadorOptional.get());
            evalucion.setId_Evaluacion(evaluacionAnterior.getId_Evaluacion());
            evaluacionRepository.save(evalucion);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
    @DeleteMapping("/{id_Evaluacion}")
    public ResponseEntity<Void> delete(@PathVariable Long id_Evaluacion) {
        if (evaluacionRepository.findById(id_Evaluacion).isPresent()) {
            evaluacionRepository.deleteById(id_Evaluacion);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
