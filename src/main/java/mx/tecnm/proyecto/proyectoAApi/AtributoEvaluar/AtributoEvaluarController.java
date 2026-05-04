package mx.tecnm.proyecto.proyectoAApi.AtributoEvaluar;

import mx.tecnm.proyecto.proyectoAApi.AtributoEgreso.AtributoEgreso;
import mx.tecnm.proyecto.proyectoAApi.AtributoEgreso.AtributoEgresoRepository;
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
@RequestMapping("/atributoEvaluar")
public class AtributoEvaluarController {
    @Autowired
    private AtributoEvaluarRepository atributoEvaluarRepository;
    @Autowired
    private AtributoEgresoRepository atributoEgresoRepository;
    @Autowired
    private EvaluacionRepository evaluacionRepository;

    @GetMapping
    public ResponseEntity<Iterable<AtributoEvaluar>> findAll(){
        return ResponseEntity.ok(atributoEvaluarRepository.findAll());
    }

    @GetMapping("/{idAevaluar}")
    public ResponseEntity<AtributoEvaluar> findById(@PathVariable Long idAevaluar) {
        Optional<AtributoEvaluar> aEvaluarOptional = atributoEvaluarRepository.findById(idAevaluar);
        if (aEvaluarOptional.isPresent()) {
            return ResponseEntity.ok(aEvaluarOptional.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<AtributoEvaluar> create(@RequestBody AtributoEvaluar newAtributoE, UriComponentsBuilder ucb){
        Optional<AtributoEgreso> egresoOptional = atributoEgresoRepository.findById(newAtributoE.getAtributoEgreso().getIdAtributo());
        Optional<Evaluacion> evaluacionOptional = evaluacionRepository.findById(newAtributoE.getEvaluacion().getIdEvaluacion());
        if(!egresoOptional.isPresent() || !evaluacionOptional.isPresent()){
            return ResponseEntity.unprocessableEntity().build();
        }

        newAtributoE.setAtributoEgreso(egresoOptional.get());
        newAtributoE.setEvaluacion(evaluacionOptional.get());
        AtributoEvaluar savedAtributoE = atributoEvaluarRepository.save(newAtributoE);
        URI uri = ucb
                .path("/atributoEvaluar/{idAevaluar}")
                .buildAndExpand(savedAtributoE.getIdAevaluar())
                .toUri();
        return ResponseEntity.created(uri).body(savedAtributoE);
    }

    @PutMapping("/{idAevaluar}")
    public ResponseEntity<Void> update(@PathVariable Long idAevaluar, @RequestBody AtributoEvaluar atributoEAct){
        Optional<AtributoEgreso> egresoOptional = atributoEgresoRepository.findById(atributoEAct.getAtributoEgreso().getIdAtributo());
        Optional<Evaluacion> evaluacionOptional = evaluacionRepository.findById(atributoEAct.getEvaluacion().getIdEvaluacion());

        if(!egresoOptional.isPresent() || !evaluacionOptional.isPresent()){
            return ResponseEntity.unprocessableEntity().build();
        }
        AtributoEvaluar atributoEAnt = atributoEvaluarRepository.findById(idAevaluar).get();
        if(atributoEAnt != null){
            atributoEAct.setAtributoEgreso(egresoOptional.get());
            atributoEAct.setEvaluacion(evaluacionOptional.get());
            atributoEAct.setIdAevaluar(idAevaluar);
            atributoEvaluarRepository.save(atributoEAct);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{idAevaluar}")
    public ResponseEntity<Void> delete(@PathVariable Long idAevaluar){
        if(atributoEvaluarRepository.findById(idAevaluar).get() != null){
            atributoEvaluarRepository.deleteById(idAevaluar);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
