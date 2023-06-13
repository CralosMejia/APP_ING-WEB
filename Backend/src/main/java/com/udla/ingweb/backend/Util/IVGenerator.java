package com.udla.ingweb.backend.Util;

import java.security.SecureRandom;

public class IVGenerator {
    public static byte[] generateIV(int length) {
        SecureRandom secureRandom = new SecureRandom();
        byte[] iv = new byte[length];
        secureRandom.nextBytes(iv);
        return iv;
    }
}
