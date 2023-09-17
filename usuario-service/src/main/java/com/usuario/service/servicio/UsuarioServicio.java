package com.usuario.service.servicio;

import com.usuario.service.entidades.Usuario;
import com.usuario.service.feignclients.AutoFeignClient;
import com.usuario.service.feignclients.MotoFeignClient;
import com.usuario.service.modelos.Auto;
import com.usuario.service.modelos.Moto;
import com.usuario.service.repositorio.UsuarioRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UsuarioServicio {

    @Autowired
    private UsuarioRepositorio usuarioRepositorio;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private AutoFeignClient autoFeignClient;

    @Autowired
    private MotoFeignClient motoFeignClient;

    public List<Auto> getAutos(Integer usuarioId) {
        Jwt jwt = (Jwt) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Authorization", "Bearer " + jwt.getTokenValue());
        ResponseEntity<List> autos = restTemplate.exchange("http://auto-service/auto/usuario/" + usuarioId, HttpMethod.GET, new HttpEntity<>(httpHeaders), List.class);
        return autos.getBody();
    }

    public List<Moto> getMotos(Integer usuarioId) {
        Jwt jwt = (Jwt) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Authorization", "Bearer " + jwt.getTokenValue());
        ResponseEntity<List> motos = restTemplate.exchange("http://moto-service/moto/usuario/" + usuarioId, HttpMethod.GET, new HttpEntity<>(httpHeaders), List.class);
        return motos.getBody();
    }

    public List<Usuario> getAll() {
        return usuarioRepositorio.findAll();
    }

    public Usuario getUsuarioById(int id) {
        return usuarioRepositorio.findById(id).orElse(null);
    }

    public Usuario save(Usuario usuario) {
        Usuario nuevoUsuario = usuarioRepositorio.save(usuario);
        return nuevoUsuario;
    }

    public Auto saveAuto(Integer usuarioId, Auto auto) {
        auto.setUsuarioId(usuarioId);
        Auto nuevoAuto = autoFeignClient.save(auto);
        return nuevoAuto;
    }

    public Moto saveMoto(Integer usuarioId, Moto moto) {
        moto.setUsuarioId(usuarioId);
        Moto nuevaMoto = motoFeignClient.save(moto);
        return nuevaMoto;
    }


    public Map<String, Object> getUsuarioAndVehiculos(Integer usuarioId) {
        Map<String, Object> resultado = new HashMap<>();
        Usuario usuario = usuarioRepositorio.findById(usuarioId).orElse(null);

        if (usuario == null) {
            resultado.put("Mensaje", "El usuario no existe");
            return resultado;
        }
        resultado.put("Usuario", usuario);
        List<Auto> autos = autoFeignClient.getAutos(usuarioId);

        if (autos.isEmpty()) {
            resultado.put("Autos", "El usuario no tiene autos");
        } else {
            resultado.put("Autos", "autos");
        }

        List<Moto> motos = motoFeignClient.getMotos(usuarioId);
        if (motos.isEmpty()) {
            resultado.put("Motos", "El usuario no tiene motos");
        } else {
            resultado.put("Motos", "motos");
        }
        return resultado;
    }
}
