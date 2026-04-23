package mx.tecnm.proyecto.proyectoAApi.AtributoEgreso;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Optional;

@RestController
@RequestMapping("/atributoEgreso")
public class AtributoEgresoController {
    @Autowired
    private AtributoEgresoRepository atributoEgresoRepository;

    @GetMapping
    public ResponseEntity<Iterable<AtributoEgreso>> findAll(){
        return ResponseEntity.ok(atributoEgresoRepository.findAll());
    }

    @GetMapping("/{id_Atributo}")
    public ResponseEntity<AtributoEgreso> findById(@PathVariable Long id_Atributo) {
        Optional<AtributoEgreso> atributoOptional = atributoEgresoRepository.findById(id_Atributo);
        if (atributoOptional.isPresent()) {
            return ResponseEntity.ok(atributoOptional.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<Void> create(@RequestBody AtributoEgreso newAtributo, UriComponentsBuilder ucb){
        AtributoEgreso savedAtributo = atributoEgresoRepository.save(newAtributo);
        URI uri = ucb
                .path("/atributoEgreso/{id_Atributo}")
                .buildAndExpand(savedAtributo.getId_Atributo())
                .toUri();
        return ResponseEntity.created(uri).build();
    }

    @PutMapping("/{id_Atributo}")
    public ResponseEntity<Void> update(@PathVariable Long id_Atributo, @RequestBody AtributoEgreso atributoAct){
        AtributoEgreso atributoAnt = atributoEgresoRepository.findById(id_Atributo).get();
        if(atributoAnt != null){
            atributoAct.setId_Atributo(atributoAnt.getId_Atributo());
            atributoEgresoRepository.save(atributoAct);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id_Atributo}")
    public ResponseEntity<Void> delete(@PathVariable Long id_Atributo){
        if(atributoEgresoRepository.findById(id_Atributo).get() != null){
            atributoEgresoRepository.deleteById(id_Atributo);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
