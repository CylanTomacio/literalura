package com.efoxdev.literalura.repositorios;

import com.efoxdev.literalura.modelos.Idiomas;
import com.efoxdev.literalura.modelos.Libros;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LibrosRepositorio extends JpaRepository<Libros, Long> {

    List<Libros> findByIdioma(Idiomas idioma);

}
