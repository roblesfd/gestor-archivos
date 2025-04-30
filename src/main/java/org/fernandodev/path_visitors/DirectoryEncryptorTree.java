package org.fernandodev.path_visitors;

import org.fernandodev.file_operations.FileEncryptor;

import javax.crypto.SecretKey;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;

import static java.nio.file.FileVisitResult.CONTINUE;

public class DirectoryEncryptorTree {
    public static void encrypt(Path sourceDir, Path targetDir, SecretKey secretKey) throws IOException {
        if(!Files.exists(sourceDir) && sourceDir.toFile().isDirectory()){
            throw new IOException("El directorio no existe o no es válido " + sourceDir);
        }

        Files.createDirectories(targetDir);

        Files.walkFileTree(sourceDir, new DirectoryEncryptorVisitor(sourceDir, targetDir, secretKey));
        System.out.println("Directorio encriptado con éxito en: " + targetDir);
    }

    private static class DirectoryEncryptorVisitor extends SimpleFileVisitor<Path>{
        Path sourceDir;
        Path targetDir;
        SecretKey secretKey;

        DirectoryEncryptorVisitor(Path srcDir, Path tDir, SecretKey secret){
            this.sourceDir = srcDir;
            this.targetDir = tDir;
            this.secretKey = secret;
        }

        @Override
        public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException{
            Path relativePath = sourceDir.relativize(dir);
            Path newDir = targetDir.resolve(relativePath);
            Files.createDirectories(newDir);
            return CONTINUE;
        }

        @Override
        public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
            Path relativePath = sourceDir.relativize(file);
            Path encryptedFile = targetDir.resolve(relativePath.toString() + ".aes");
            try{
                FileEncryptor.encryptFile(file, encryptedFile, secretKey);
            }catch(Exception ex){
                throw new IOException("Error al encriptar el archivo: " + file, ex);
            }
            return CONTINUE;
        }
    }
}
