package com.udla.ingweb.backend.Util;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class ClaveUtils {
    public static byte[] generarClave(String clave) throws NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] claveBytes = clave.getBytes(StandardCharsets.UTF_8);
        byte[] hash = digest.digest(claveBytes);

        // Obtener solo los primeros 16 bytes (128 bits)
        byte[] claveBytes128 = new byte[16];
        System.arraycopy(hash, 0, claveBytes128, 0, 16);

        return claveBytes128;
    }
}
