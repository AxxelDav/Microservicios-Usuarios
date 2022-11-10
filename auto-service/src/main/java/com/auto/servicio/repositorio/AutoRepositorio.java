package com.auto.servicio.repositorio;

import com.auto.servicio.entidades.Auto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AutoRepositorio extends JpaRepository<Auto, Integer> {

    List<Auto> findByUsuarioId(Integer usuarioId);
}
