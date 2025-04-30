package org.fernandodev.file_operations;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.file.Files;
import java.nio.file.Path;

public class FileEncryptor {
    public static void encryptFile(Path inputPath, Path outputPath, SecretKey key) throws Exception{
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE, key);
        byte[] fileBytes = Files.readAllBytes(inputPath);
        byte[] encryptedBytes = cipher.doFinal(fileBytes);
        Files.write(outputPath, encryptedBytes);
        System.out.println("Archivo encriptado con éxito en: " + outputPath);
    }

    public static void decryptFile(Path encryptedPath, Path decryptedPath, SecretKey key) throws Exception{
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.DECRYPT_MODE, key);

        byte[] encryptedBytes = Files.readAllBytes(encryptedPath);
        byte[] decryptedBytes = cipher.doFinal(encryptedBytes);

        Files.write(decryptedPath, decryptedBytes);
        System.out.println("Archivo descifrado con éxito en: " + decryptedPath);
    }

    public static SecretKey generateKey(){
        byte[] keyBytes = "1234567890123456".getBytes(); //16 bytes para AES-128
        return new SecretKeySpec(keyBytes, "AES");
    }
}
