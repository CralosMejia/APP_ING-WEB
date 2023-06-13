package com.udla.ingweb.backend.Util;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.springframework.stereotype.Component;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.Security;

@Component
public class OpenSSLUtil {

    private static final String ALGORITHM = "AES/CBC/PKCS5Padding";
    private static final String PROVIDER = "BC";

    public byte[] encrypt(byte[] input, byte[] keyBytes, byte[] iv) throws Exception {
        Security.addProvider(new BouncyCastleProvider());

        SecretKey secretKey = new SecretKeySpec(keyBytes, "AES");
        Cipher cipher = Cipher.getInstance(ALGORITHM, PROVIDER);
        IvParameterSpec ivSpec = new IvParameterSpec(iv);

        cipher.init(Cipher.ENCRYPT_MODE, secretKey, ivSpec);
        return cipher.doFinal(input);
    }

}
