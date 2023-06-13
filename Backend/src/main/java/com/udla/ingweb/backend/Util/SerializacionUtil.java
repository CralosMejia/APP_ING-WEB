package com.udla.ingweb.backend.Util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.udla.ingweb.backend.Entity.Product;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.List;

public class SerializacionUtil {
    public static byte[] convertirListaObjetosABytes(List<Product> listaObjetos) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsBytes(listaObjetos);
    }
}
