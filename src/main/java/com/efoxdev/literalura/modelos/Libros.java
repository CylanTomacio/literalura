package com.efoxdev.literalura.modelos;

import jakarta.persistence.*;

@Entity
@Table(name = "libros")
public class Libros {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long Id;
    @Column(unique = true)
    private String titulo;
    @ManyToOne
    private Autores autor;

    @Enumerated(EnumType.STRING)
    private Idiomas idioma;
    private Integer numeroDeDescargas;

    //Constructors
    public Libros() {}

    public Libros(DatosLibros datosLibros) {
        this.titulo = datosLibros.titulo();
        this.numeroDeDescargas = datosLibros.descargas();
        this.autor = new Autores( datosLibros.autores().getFirst() );
        this.idioma = Idiomas.fromString(  datosLibros.idiomas().get(0) );
    }

    @Override
    public String toString() {
        return "-------- Libro --------" +
                "\nTítulo: " + titulo +
                "\nAutor: " + autor.getNombre() +
                "\nIdioma: " + idioma +
                "\nNúmero de descargas: " + numeroDeDescargas + "\n";
    }

//Getters and Setters

    public long getId() {
        return Id;
    }

    public void setId(long id) {
        Id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Autores getAutor() {
        return autor;
    }

    public void setAutores(Autores autor) {
        this.autor = autor;
    }

    public void setAutor(Autores autor) {
        this.autor = autor;
    }

    public Idiomas getIdioma() {
        return idioma;
    }

    public void setIdioma(Idiomas idioma) {
        this.idioma = idioma;
    }

    public Integer getNumeroDeDescargas() {
        return numeroDeDescargas;
    }

    public void setNumeroDeDescargas(Integer numeroDeDescargas) {
        this.numeroDeDescargas = numeroDeDescargas;
    }
}
