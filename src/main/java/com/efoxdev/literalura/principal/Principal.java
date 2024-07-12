package com.efoxdev.literalura.principal;

import com.efoxdev.literalura.modelos.*;
import com.efoxdev.literalura.repositorios.AutoresRepositorio;
import com.efoxdev.literalura.repositorios.LibrosRepositorio;
import com.efoxdev.literalura.servicios.ConexionApi;
import com.efoxdev.literalura.servicios.TransformarDatos;
import org.springframework.dao.DataIntegrityViolationException;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class Principal {

    private final Scanner entradaUsuario = new Scanner(System.in);
    private final ConexionApi conexionApiGutendex = new ConexionApi();
    private final TransformarDatos transformarDatos = new TransformarDatos();

    private final LibrosRepositorio librosRepositorio;
    private final AutoresRepositorio autoresRepositorio;

    private List<Libros> libros;
    private List<Autores> autores;

    public Principal(LibrosRepositorio librosRepositorio, AutoresRepositorio autoresRepositorio) {
        this.librosRepositorio = librosRepositorio;
        this.autoresRepositorio = autoresRepositorio;
    }

    public void mostrarMenu(){
        boolean salir = false;
        int seleccionUsuario;
        final String menu = """
                *********************************************************
                **************   Bienvenido a LiterAlura   **************
                *********************************************************
                
                Aquí puedes buscar en un amplío catálogo de libros.
                
                Selecciona una opción:
                
                1. Registrar/Buscar libro por título.
                2. Listar libros registrados.
                3. Listar autores registrados.
                4. Listar autores vivos en un determinado año.
                5. Listar libros por idioma.
                0. Salir
                """;

        while(!salir) {
            System.out.println(menu);
            try {
                seleccionUsuario = entradaUsuario.nextInt();
                entradaUsuario.nextLine();
                switch (seleccionUsuario) {
                    case 1:
                        buscarLibros();
                        break;
                    case 2:
                        listarLibrosRegistrados();
                        break;
                    case 3:
                        listarAutoresRegistrados();
                        break;
                    case 4:
                        listarAutoresVivosEnDeterminadoAnio();
                        break;
                    case 5:
                        listarLibrosPorIdioma();
                        break;
                    case 0:
                        System.out.println("Gracias por utilizar LiterAlura");
                        System.out.println("Saliendo...");
                        salir = true;
                        break;
                    default:
                        System.out.println("Opción inválida");
                        break;
                }
            } catch (InputMismatchException e) {
                System.out.println("Selecciona únicamente una de las opciones ofrecidas");
                entradaUsuario.next();
            }
        }
    }

    private Resultados getDatosLibros() {
        System.out.println("Escribe el nombre del libro que buscas");
        String nombreLibro = entradaUsuario.nextLine();
        String URL = "https://gutendex.com/books/?";
        String json = conexionApiGutendex.obtenerDatos(URL + "search=" + URLEncoder.encode(nombreLibro, StandardCharsets.UTF_8) );
        return transformarDatos.obtenerDatos(json, Resultados.class);
    }

    private void buscarLibros() {
        Resultados datosLibros = getDatosLibros();
        if(datosLibros.resultados().isEmpty()) {
            System.out.println("No se ha encontrado ningún libro.");
            return;
        }
        DatosLibros primerLibro = datosLibros.resultados().getFirst();

        Libros libro = new Libros(primerLibro);
        Autores autor = libro.getAutor();

        System.out.println(libro);

        List<Libros> listaDeLibros = autor.getLibros();
        Optional<Autores> autorOpcional = autoresRepositorio.findByNombreIgnoreCase( autor.getNombre() );

        if(listaDeLibros == null) {
            listaDeLibros = new ArrayList<>();
        }

        if( autorOpcional.isPresent() ) {
            try {
                libro.setAutores( autorOpcional.get() );
                librosRepositorio.save(libro);
                System.out.println("Libro registrado exitosamente");
                return;
            } catch (DataIntegrityViolationException error) {
                System.out.println("Este libro ya fue registrado anteriormente");
                return;
            }

        }

        listaDeLibros.add(libro);
        autor.setLibros(listaDeLibros);
        autoresRepositorio.save(autor);
        System.out.println("Libro registrado exitosamente");
    }

    private void listarLibrosRegistrados() {
        libros = librosRepositorio.findAll();
        if (libros.isEmpty()) {
            System.out.println("Aún no hay ningún libro registrado");
        } else {
            libros.forEach(System.out::println);
        }
    }

    private void listarAutoresRegistrados() {
        autores = autoresRepositorio.findAll();
        if (autores.isEmpty()) {
            System.out.println("Aún no hay autores registrados");
        } else {
            autores.forEach(System.out::println);
        }
    }

    private void listarAutoresVivosEnDeterminadoAnio() {
        System.out.println("Escribe el año que deseas buscar: ");
        int anioUsuario = entradaUsuario.nextInt();
        autores = autoresRepositorio.autoresVivosEnDeterminadoAnio(anioUsuario);
        if( autores.isEmpty() ) {
            System.out.println("No se encontraron autores vivos en ese año");
        } else {
            autores.forEach(System.out::println);
        }

    }

    private void listarLibrosPorIdioma() {
        System.out.println("Escribe el idioma del libro que deseas buscar: ");
        for(Idiomas idioma : Idiomas.values()) {
            System.out.println(" - " + idioma.getIdiomaLibro() + " (" + idioma + ")" );
        }
        try {
            String idiomaUsuario = entradaUsuario.nextLine();
            var busquedaIdioma = Idiomas.fromString(idiomaUsuario);
            libros = librosRepositorio.findByIdioma(busquedaIdioma);
            if( libros.isEmpty() ) {
                System.out.println("No se encontró ningún libro en ese idioma");
            } else {
                libros.forEach(System.out::println);
            }
        } catch (IllegalArgumentException error) {
            System.out.println("Escribe el idioma en el formato correcto.");
            entradaUsuario.next();
        }
    }
}
