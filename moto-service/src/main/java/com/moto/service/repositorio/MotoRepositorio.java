package com.moto.service.repositorio;

import com.moto.service.entidades.Moto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MotoRepositorio extends JpaRepository<Moto, Integer> {

    List<Moto> findByUsuarioId(Integer usuarioId);
}
