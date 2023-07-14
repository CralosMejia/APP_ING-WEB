package com.udla.ingweb.backend.Util;

import org.springframework.stereotype.Service;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.core.SdkBytes;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.kms.KmsClient;
import software.amazon.awssdk.services.kms.model.EncryptRequest;
import software.amazon.awssdk.services.kms.model.EncryptResponse;

import java.io.UnsupportedEncodingException;
import java.util.Base64;

@Service
public class EncryptionService {
    private final KmsClient kmsClient;

    public EncryptionService() {
        String accessKeyId = "AKIATWFYCVWD73BPM7VK";
        String secretAccessKey = "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";
        Region region = Region.US_EAST_1; // Reemplaza por la región de AWS que estás utilizando

        AwsBasicCredentials credentials = AwsBasicCredentials.create(accessKeyId, secretAccessKey);
        kmsClient = KmsClient.builder()
                .region(region)
                .credentialsProvider(StaticCredentialsProvider.create(credentials))
                .build();
    }

    public String encryptData(String data) throws UnsupportedEncodingException {
        byte[] dataBytes = data.getBytes("UTF-8");
        EncryptRequest request = EncryptRequest.builder()
                .keyId("021e84f8-83bb-4318-aec2-b2effa43691e") // Reemplaza con el ID de tu clave KMS
                .plaintext(SdkBytes.fromByteArray(dataBytes))
                .build();
        EncryptResponse response = kmsClient.encrypt(request);

        byte[] encryptedBytes = response.ciphertextBlob().asByteArray();
        String encryptedData = Base64.getEncoder().encodeToString(encryptedBytes);
        return encryptedData;
    }
}
