package mx.tecnm.proyecto.proyectoAApi.Evaluador;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Optional;

@CrossOrigin(origins="http://localhost:5173")
@RestController
@RequestMapping("/evaluador")
public class EvaluadorController {
    @Autowired
    EvaluadorRepository evaluadorRepository;

    @GetMapping()
    public ResponseEntity<Iterable<Evaluador>> findAll() {
        return ResponseEntity.ok(evaluadorRepository.findAll());
    }
    @GetMapping("/{idEvaluador}")
    public ResponseEntity<Evaluador> findById(@PathVariable Long idEvaluador) {
        Optional<Evaluador> evaluador0ptional = evaluadorRepository.findById(idEvaluador);
        if (evaluador0ptional.isPresent()) {
            return ResponseEntity.ok(evaluador0ptional.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    @PostMapping
    public ResponseEntity<Void> create(@RequestBody Evaluador newEvaluador, UriComponentsBuilder ucb) {
        Evaluador savedEvaluador = evaluadorRepository.save(newEvaluador);
        URI uri = ucb
                .path("evaluador/{idEvaluador}")
                .buildAndExpand(savedEvaluador.getIdEvaluador())
                .toUri();
        return ResponseEntity.created(uri).build();
    }
    @PutMapping("/{idEvaluador}")
    public ResponseEntity<Void> update(@PathVariable Long idEvaluador, @RequestBody Evaluador evaluadorAct) {
        Evaluador evaluadorAnt = evaluadorRepository.findById(idEvaluador).get();
        if (evaluadorAnt != null) {
            evaluadorAct.setIdEvaluador(evaluadorAnt.getIdEvaluador());
            evaluadorRepository.save(evaluadorAct);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
    @DeleteMapping("/{idEvaluador}")
    public ResponseEntity<Void> delete(@PathVariable Long idEvaluador) {
        if (evaluadorRepository.findById(idEvaluador).get() != null) {
            evaluadorRepository.deleteById(idEvaluador);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
