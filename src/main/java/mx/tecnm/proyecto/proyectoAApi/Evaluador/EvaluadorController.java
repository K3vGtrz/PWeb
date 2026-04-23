package mx.tecnm.proyecto.proyectoAApi.Evaluador;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Optional;

@RestController
@RequestMapping("/evaluador")
public class EvaluadorController {
    @Autowired
    EvaluadorRepository evaluadorRepository;

    @GetMapping()
    public ResponseEntity<Iterable<Evaluador>> findAll() {
        return ResponseEntity.ok(evaluadorRepository.findAll());
    }
    @GetMapping("/{id_Evaluador}")
    public ResponseEntity<Evaluador> findById(@PathVariable Long id_Evaluador) {
        Optional<Evaluador> evaluador0ptional = evaluadorRepository.findById(id_Evaluador);
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
                .path("evaluador/{id_Evaluador}")
                .buildAndExpand(savedEvaluador.getId_Evaluador())
                .toUri();
        return ResponseEntity.created(uri).build();
    }
    @PutMapping("/{id_Evaluador}")
    public ResponseEntity<Void> update(@PathVariable Long id_Evaluador, @RequestBody Evaluador evaluadorAct) {
        Evaluador evaluadorAnt = evaluadorRepository.findById(id_Evaluador).get();
        if (evaluadorAnt != null) {
            evaluadorAct.setId_Evaluador(evaluadorAnt.getId_Evaluador());
            evaluadorRepository.save(evaluadorAct);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
    @DeleteMapping("/{id_Evaluador}")
    public ResponseEntity<Void> delete(@PathVariable Long id_Evaluador) {
        if (evaluadorRepository.findById(id_Evaluador).get() != null) {
            evaluadorRepository.deleteById(id_Evaluador);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
