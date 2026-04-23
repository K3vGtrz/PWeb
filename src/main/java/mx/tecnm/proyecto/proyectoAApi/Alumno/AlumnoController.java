package mx.tecnm.proyecto.proyectoAApi.Alumno;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Optional;

@RestController
@RequestMapping("/alumno")
public class AlumnoController {
    @Autowired
    private AlumnoRepository alumnoRepository;

    @GetMapping
    public ResponseEntity<Iterable<Alumno>> findAll(){
        return ResponseEntity.ok(alumnoRepository.findAll());
    }

    @GetMapping("/{numControl}")
    public ResponseEntity<Alumno> findById(@PathVariable String numControl) {
        Optional<Alumno> alumnoOptional = alumnoRepository.findById(numControl);
        if (alumnoOptional.isPresent()) {
            return ResponseEntity.ok(alumnoOptional.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<Alumno> create(@RequestBody Alumno newAlumno, UriComponentsBuilder ucb){
        Alumno savedAlumno = alumnoRepository.save(newAlumno);
        URI uri = ucb
                .path("/alumno/{numControl}")
                .buildAndExpand(savedAlumno.getNumControl())
                .toUri();
        return ResponseEntity.created(uri).build();
    }

    @PutMapping("/{numControl}")
    public ResponseEntity<Void> update(@PathVariable String numControl, @RequestBody Alumno alumnoAct){
        Alumno alumnoAnt = alumnoRepository.findById(numControl).get();
        if(alumnoAnt != null){
            alumnoAct.setNumControl(alumnoAnt.getNumControl());
            alumnoRepository.save(alumnoAct);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{numControl}")
    public ResponseEntity<Void> delete(@PathVariable String numControl){
        if(alumnoRepository.findById(numControl).get() != null){
            alumnoRepository.deleteById(numControl);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}