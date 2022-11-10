package com.auto.servicio.controlador;

import com.auto.servicio.entidades.Auto;
import com.auto.servicio.servicio.AutoServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/auto")
public class AutoControlador {

    @Autowired
    private AutoServicio autoServicio;

    @GetMapping
    public ResponseEntity<List<Auto>> listarAutos() {
        List<Auto> autos = autoServicio.getAll();
        if(autos.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(autos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Auto> obtenerAuto(@PathVariable("id") int id) {
        Auto auto = autoServicio.getAutoById(id);
        if(auto == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(auto);
    }

    @PostMapping
    public ResponseEntity<Auto> guardarAuto(@RequestBody Auto auto){
        Auto nuevoAuto = autoServicio.save(auto);
        return ResponseEntity.ok(nuevoAuto);
    }

    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<Auto>> listarAutosPorUsuarioId(@PathVariable("usuarioId") Integer id) {
        List<Auto> autos = autoServicio.findByUsuarioId(id);
        if(autos.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(autos);
    }

}
