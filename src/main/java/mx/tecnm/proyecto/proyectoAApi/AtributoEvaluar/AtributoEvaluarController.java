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

    @GetMapping("/{id_AtributoEvaluar}")
    public ResponseEntity<AtributoEvaluar> findById(@PathVariable Long id_AtributoEvaluar) {
        Optional<AtributoEvaluar> aEvaluarOptional = atributoEvaluarRepository.findById(id_AtributoEvaluar);
        if (aEvaluarOptional.isPresent()) {
            return ResponseEntity.ok(aEvaluarOptional.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<AtributoEvaluar> create(@RequestBody AtributoEvaluar newAtributoE, UriComponentsBuilder ucb){
        Optional<AtributoEgreso> egresoOptional = atributoEgresoRepository.findById(newAtributoE.getAtributoEgreso().getId_Atributo());
        Optional<Evaluacion> evaluacionOptional = evaluacionRepository.findById(newAtributoE.getEvaluacion().getId_Evaluacion());
        if(!egresoOptional.isPresent() || !evaluacionOptional.isPresent()){
            return ResponseEntity.unprocessableEntity().build();
        }

        newAtributoE.setAtributoEgreso(egresoOptional.get());
        newAtributoE.setEvaluacion(evaluacionOptional.get());
        AtributoEvaluar savedAtributoE = atributoEvaluarRepository.save(newAtributoE);
        URI uri = ucb
                .path("/atributoEvaluar/{id_AtributoEvaluar}")
                .buildAndExpand(savedAtributoE.getId_AtributoEvaluar())
                .toUri();
        return ResponseEntity.created(uri).body(savedAtributoE);
    }

    @PutMapping("/{id_AtributoEvaluar}")
    public ResponseEntity<Void> update(@PathVariable Long id_AtributoEvaluar, @RequestBody AtributoEvaluar atributoEAct){
        Optional<AtributoEgreso> egresoOptional = atributoEgresoRepository.findById(atributoEAct.getAtributoEgreso().getId_Atributo());
        Optional<Evaluacion> evaluacionOptional = evaluacionRepository.findById(atributoEAct.getEvaluacion().getId_Evaluacion());

        if(!egresoOptional.isPresent() || !evaluacionOptional.isPresent()){
            return ResponseEntity.unprocessableEntity().build();
        }
        AtributoEvaluar atributoEAnt = atributoEvaluarRepository.findById(id_AtributoEvaluar).get();
        if(atributoEAnt != null){
            atributoEAct.setAtributoEgreso(egresoOptional.get());
            atributoEAct.setEvaluacion(evaluacionOptional.get());
            atributoEAct.setId_AtributoEvaluar(id_AtributoEvaluar);
            atributoEvaluarRepository.save(atributoEAct);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id_AtributoEvaluar}")
    public ResponseEntity<Void> delete(@PathVariable Long id_AtributoEvaluar){
        if(atributoEvaluarRepository.findById(id_AtributoEvaluar).get() != null){
            atributoEvaluarRepository.deleteById(id_AtributoEvaluar);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
