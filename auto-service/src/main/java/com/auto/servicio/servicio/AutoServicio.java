package com.auto.servicio.servicio;

import com.auto.servicio.entidades.Auto;
import com.auto.servicio.repositorio.AutoRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AutoServicio {

    @Autowired
    private AutoRepositorio autoRepositorio;

    public List<Auto> getAll() {
        return autoRepositorio.findAll();
    }

    public Auto getAutoById(int id) {
        return autoRepositorio.findById(id).orElse(null);
    }

    public Auto save(Auto auto) {
        Auto nuevoAuto = autoRepositorio.save(auto);
        return nuevoAuto;
    }

    public List<Auto> findByUsuarioId(Integer usuarioId){
        return autoRepositorio.findByUsuarioId(usuarioId);
    }
}
