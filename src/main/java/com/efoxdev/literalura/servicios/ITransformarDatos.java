package com.efoxdev.literalura.servicios;

public interface ITransformarDatos {
    <T> T obtenerDatos(String json, Class<T> clase);
}
