package com.moto.service.controlador;

import com.moto.service.entidades.Moto;
import com.moto.service.servicio.MotoServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/moto")
public class MotoControlador {

    @Autowired
    private MotoServicio motoServicio;

    @GetMapping
    public ResponseEntity<List<Moto>> listarAutos() {
        List<Moto> motos = motoServicio.getAll();
        if(motos.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(motos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Moto> obtenerMoto(@PathVariable("id") int id) {
        Moto moto = motoServicio.getMotoById(id);
        if(moto == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(moto);
    }

    @PostMapping
    public ResponseEntity<Moto> guardarMoto(@RequestBody Moto moto){
        Moto nuevaMoto = motoServicio.save(moto);
        return ResponseEntity.ok(nuevaMoto);
    }

    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<Moto>> listarMotosPorUsuarioId(@PathVariable("usuarioId") Integer id) {
        List<Moto> motos = motoServicio.findByUsuarioId(id);
        if(motos.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(motos);
    }
}
