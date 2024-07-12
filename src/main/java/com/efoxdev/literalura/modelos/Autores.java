package com.efoxdev.literalura.modelos;


import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "autores")
public class Autores {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;
    @Column(unique = true)
    private String nombre;
    private Integer anioNacimiento;
    private Integer anioFallecimiento;

    @OneToMany(mappedBy = "autor", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Libros> libros;

    public Autores() {}

    public Autores(DatosAutores datosAutores) {
        this.nombre = datosAutores.nombre();
        this.anioNacimiento = datosAutores.anioNacimiento();
        this.anioFallecimiento = datosAutores.anioFallecimiento();
    }

    @Override
    public String toString() {
        List<String> nombreDeLibros = new ArrayList<>();
        for (Libros libro: libros) {
            nombreDeLibros.add( libro.getTitulo() );
        }
        return "\nAutor: " + nombre +
                "\nAño de Nacimiento: " + anioNacimiento +
                "\nAño de fallecimiento: " + anioFallecimiento +
                "\nLibros: " + nombreDeLibros + "\n";
    }

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public List<Libros> getLibros() {
        return libros;
    }

    public void setLibros(List<Libros> libros) {
        for(Libros libro : libros) {
            libro.setAutores(this);
        }
        this.libros = libros;
    }

    public Integer getAnioFallecimiento() {
        return anioFallecimiento;
    }

    public void setAnioFallecimiento(Integer anioFallecimiento) {
        this.anioFallecimiento = anioFallecimiento;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Integer getAnioNacimiento() {
        return anioNacimiento;
    }

    public void setAnioNacimiento(Integer anioNacimiento) {
        this.anioNacimiento = anioNacimiento;
    }
}
