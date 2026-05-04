package mx.tecnm.proyecto.proyectoAApi.Alumno;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/alumno")
public class AlumnoController {
    @Autowired
    private AlumnoRepository alumnoRepository;

    @GetMapping
    public ResponseEntity<Iterable<Alumno>> findAll() {
        return ResponseEntity.ok(alumnoRepository.findAll());
    }

    @GetMapping("/{idAlumno}")
    public ResponseEntity<Alumno> findById(@PathVariable Long idAlumno) {
        Optional<Alumno> alumnoOptional = alumnoRepository.findById(idAlumno);
        if (alumnoOptional.isPresent()) {
            return ResponseEntity.ok(alumnoOptional.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<Alumno> create(@RequestBody Alumno newAlumno, UriComponentsBuilder ucb) {
        Alumno savedAlumno = alumnoRepository.save(newAlumno);
        URI uri = ucb
                .path("/alumno/{idAlumno}")
                .buildAndExpand(savedAlumno.getNumControl())
                .toUri();
        return ResponseEntity.created(uri).build();
    }

    @PutMapping("/{idAlumno}")
    public ResponseEntity<Void> update(@PathVariable Long idAlumno, @RequestBody Alumno alumnoAct) {
        Alumno alumnoAnt = alumnoRepository.findById(idAlumno).get();
        if (alumnoAnt != null) {
            alumnoAct.setNumControl(alumnoAnt.getNumControl());
            alumnoRepository.save(alumnoAct);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{idAlumno}")
    public ResponseEntity<Void> delete(@PathVariable Long idAlumno) {
        if (alumnoRepository.findById(idAlumno).get() != null) {
            alumnoRepository.deleteById(idAlumno);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}