package com.the.pet.service;

import com.oracle.bmc.auth.ConfigFileAuthenticationDetailsProvider;
import com.oracle.bmc.objectstorage.ObjectStorageClient;
import com.oracle.bmc.objectstorage.requests.PutObjectRequest;
import com.oracle.bmc.objectstorage.responses.PutObjectResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

@Service
public class ObjectStorageService {

    @Value("${oracle.oci.config.path}")
    private String configFilePath;

    @Value("${oracle.oci.config.profile}")
    private String profile;

    @Value("${oracle.object-storage.namespace}")
    private String namespaceName;

    @Value("${oracle.object-storage.bucket-name}")
    private String bucketName;

    public void uploadPhoto(String filePath, String objectName) throws Exception {
        ConfigFileAuthenticationDetailsProvider provider = new ConfigFileAuthenticationDetailsProvider(configFilePath, profile);
        ObjectStorageClient objectStorageClient = new ObjectStorageClient(provider);

        byte[] fileContent = Files.readAllBytes(Paths.get(filePath));

        PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                .bucketName(bucketName)
                .namespaceName(namespaceName)
                .objectName(objectName)
                .putObjectBody(new ByteArrayInputStream(fileContent))
                .contentLength((long) fileContent.length)
                .build();

        PutObjectResponse response = objectStorageClient.putObject(putObjectRequest);
        System.out.println("Uploaded: " + response.getETag());
    }
}
