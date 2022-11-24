package com.usuario.service.controlador;

import com.usuario.service.entidades.Usuario;
import com.usuario.service.modelos.Auto;
import com.usuario.service.modelos.Moto;
import com.usuario.service.servicio.UsuarioServicio;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/usuario")
public class UsuarioControlador {

    @Autowired
    private UsuarioServicio usuarioServicio;

    @GetMapping
    public ResponseEntity<List<Usuario>> listarUsuarios() {
        List<Usuario> usuarios = usuarioServicio.getAll();
        if(usuarios.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(usuarios);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Usuario> obtenerUsuario(@PathVariable("id") int id) {
        Usuario usuario = usuarioServicio.getUsuarioById(id);
        if(usuario == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(usuario);
    }

    @PostMapping
    public ResponseEntity<Usuario> guardarUsuario(@RequestBody Usuario usuario){
        Usuario nuevoUsuario = usuarioServicio.save(usuario);
        return ResponseEntity.ok(nuevoUsuario);
    }

    @CircuitBreaker(name = "autosCB",fallbackMethod = "fallBackGetAutos")
    @GetMapping("autos/{usuarioId}")
    public ResponseEntity<List<Auto>> getAutos(@PathVariable("usuarioId") Integer id) {
        Usuario usuario = usuarioServicio.getUsuarioById(id);
        if (usuario == null)
            return ResponseEntity.notFound().build();
        List<Auto> autos = usuarioServicio.getAutos(id);
        return ResponseEntity.ok(autos);
    }

    @CircuitBreaker(name = "motosCB", fallbackMethod = "fallBackGetMotos")
    @GetMapping("motos/{usuarioId}")
    public ResponseEntity<List<Moto>> getMotos(@PathVariable("usuarioId") Integer id) {
        Usuario usuario = usuarioServicio.getUsuarioById(id);
        if (usuario == null)
            return ResponseEntity.notFound().build();
        List<Moto> motos = usuarioServicio.getMotos(id);
        return ResponseEntity.ok(motos);
    }

    @CircuitBreaker(name = "carrosCB", fallbackMethod = "fallBackSaveAuto")
    @PostMapping("/auto/{usuarioId}")
    public ResponseEntity<Auto> guardarAuto(@PathVariable("usuarioId") Integer usuarioId, @RequestBody Auto auto) {
        Auto nuevoAuto = usuarioServicio.saveAuto(usuarioId, auto);
        return ResponseEntity.ok(nuevoAuto);
    }

    @CircuitBreaker(name = "motosCB", fallbackMethod = "fallBackSaveMoto")
    @PostMapping("/moto/{usuarioId}")
    public ResponseEntity<Moto> guardarMoto(@PathVariable("usuarioId") Integer usuarioId, @RequestBody Moto moto) {
        Moto nuevaMoto = usuarioServicio.saveMoto(usuarioId, moto);
        return ResponseEntity.ok(nuevaMoto);
    }

    @CircuitBreaker(name = "todosCB", fallbackMethod = "fallBackGetTodos")
    @GetMapping("/todos/{usuarioId}")
    public ResponseEntity<Map<String, Object>> listarTodosLosVehiculos(@PathVariable("usuarioId") Integer usuarioId) {
        Map<String, Object> resultado = usuarioServicio.getUsuarioAndVehiculos(usuarioId);
        return ResponseEntity.ok(resultado);
    }

    //Metodos FallBack
    private ResponseEntity<List<Auto>> fallBackGetAutos(@PathVariable("usuarioId") Integer id,
                                                          RuntimeException excepcion) {
        return new ResponseEntity("El usuario : " + id + " tiene los autos en el taller", HttpStatus.OK);
    }

    private ResponseEntity<Auto> fallBackSaveAutos(@PathVariable("usuarioId") Integer id, @RequestBody Auto auto,
                                                    RuntimeException excepcion) {
        return new ResponseEntity("El usuario : " + id + " no tiene dinero para los autos", HttpStatus.OK);
    }

    private ResponseEntity<List<Moto>> fallBackGetMotos(@PathVariable("usuarioId") Integer id, RuntimeException excepcion) {
        return new ResponseEntity("El usuario : " + id + " tiene las motos en el taller", HttpStatus.OK);
    }

    private ResponseEntity<Moto> fallBackSaveMoto(@PathVariable("usuarioId") Integer id, @RequestBody Moto carro,
                                                  RuntimeException excepcion) {
        return new ResponseEntity("El usuario : " + id + " no tiene dinero para las motos", HttpStatus.OK);
    }

    private ResponseEntity<Map<String, Object>> fallBackGetTodos(@PathVariable("usuarioId") Integer id,
                                                                 RuntimeException excepcion) {
        return new ResponseEntity("El usuario : " + id + " tiene los vehiculos en el taller", HttpStatus.OK);
    }


}
