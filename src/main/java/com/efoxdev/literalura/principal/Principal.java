package com.efoxdev.literalura.principal;

public class Principal {

    public void mostrarMenu(){
        System.out.println("""
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
                """);
    }
}
