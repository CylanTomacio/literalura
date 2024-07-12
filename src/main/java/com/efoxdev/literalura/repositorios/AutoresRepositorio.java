package com.efoxdev.literalura.repositorios;

import com.efoxdev.literalura.modelos.Autores;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface AutoresRepositorio extends JpaRepository<Autores, Long> {
    Optional<Autores> findByNombreIgnoreCase(String nombre);
    @Query(value = "SELECT a FROM Autores a WHERE a.anioNacimiento <= :anio AND a.anioFallecimiento >= :anio")
    List<Autores> autoresVivosEnDeterminadoAnio(Integer anio);
}
