package com.efoxdev.literalura.modelos;

public enum Idiomas {
    ESPANOL("es"),
    INGLES("en"),
    PORTUGUES("pt"),
    FRANCES("fr"),
    ALEMAN("de"),
    CHINO("zh"),
    RUSO("ru"),
    COREANO("ko"),
    ESPERANTO("eo"),
    FINES("fi"),
    HEBREO("he"),
    HINDI("hi"),
    HUNGARO("hu"),
    INDONESIO("id"),
    ITALIANO("it"),
    JAPONES("ja"),
    NEERLANDES("nl"),
    NORUEGO("no"),
    POLACO("pl"),
    SUECO("sv"),
    TAILANDES("th");

    private final String idiomaLibro;

    Idiomas(String idioma) {
        this.idiomaLibro = idioma;
    }

    public static Idiomas fromString(String texto) {
        for ( Idiomas idioma : Idiomas.values() ) {
            if( idioma.idiomaLibro.equalsIgnoreCase(texto) && !texto.isEmpty() ) {
                return idioma;
            }
        }
        throw new IllegalArgumentException("Ningún idioma encontrado: " + texto);
    }

    public String getIdiomaLibro() {
        return idiomaLibro;
    }
}
