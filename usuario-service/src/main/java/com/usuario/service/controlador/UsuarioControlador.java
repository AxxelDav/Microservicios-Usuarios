package com.usuario.service.controlador;

import com.usuario.service.entidades.Usuario;
import com.usuario.service.modelos.Auto;
import com.usuario.service.modelos.Moto;
import com.usuario.service.servicio.UsuarioServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("autos/{usuarioId}")
    public ResponseEntity<List<Auto>> getAutos(@PathVariable("usuarioId") Integer id) {
        Usuario usuario = usuarioServicio.getUsuarioById(id);
        if (usuario == null)
            return ResponseEntity.notFound().build();
        List<Auto> autos = usuarioServicio.getAutos(id);
        return ResponseEntity.ok(autos);
    }

    @GetMapping("motos/{usuarioId}")
    public ResponseEntity<List<Moto>> getMotos(@PathVariable("usuarioId") Integer id) {
        Usuario usuario = usuarioServicio.getUsuarioById(id);
        if (usuario == null)
            return ResponseEntity.notFound().build();
        List<Moto> motos = usuarioServicio.getMotos(id);
        return ResponseEntity.ok(motos);
    }

    @PostMapping("/auto/{usuarioId}")
    public ResponseEntity<Auto> guardarAuto(@PathVariable("usuarioId") Integer usuarioId, @RequestBody Auto auto) {
        Auto nuevoAuto = usuarioServicio.saveAuto(usuarioId, auto);
        return ResponseEntity.ok(nuevoAuto);
    }

    @PostMapping("/moto/{usuarioId}")
    public ResponseEntity<Moto> guardarMoto(@PathVariable("usuarioId") Integer usuarioId, @RequestBody Moto moto) {
        Moto nuevaMoto = usuarioServicio.saveMoto(usuarioId, moto);
        return ResponseEntity.ok(nuevaMoto);
    }

    @GetMapping("/todos/{usuarioId}")
    public ResponseEntity<Map<String, Object>> listarTodosLosVehiculos(@PathVariable("usuarioId") Integer usuarioId) {
        Map<String, Object> resultado = usuarioServicio.getUsuarioAndVehiculos(usuarioId);
        return ResponseEntity.ok(resultado);
    }

}
