package org.fernandodev.path_visitors;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class DirectoryDecompressorTree {

    public static void extractZip(Path zipFilePath, Path targetDir) throws IOException {
        if (!Files.exists(zipFilePath) || !zipFilePath.toString().endsWith(".zip")) {
            throw new IOException("Archivo ZIP inválido o no existe: " + zipFilePath);
        }

        Files.createDirectories(targetDir);

        try (ZipInputStream zipInputStream = new ZipInputStream(new FileInputStream(zipFilePath.toFile()))) {
            ZipEntry entry;

            while ((entry = zipInputStream.getNextEntry()) != null) {
                Path extractedPath = targetDir.resolve(entry.getName()).normalize();

                if (!extractedPath.startsWith(targetDir)) {
                    throw new IOException("Entrada ZIP maliciosa detectada: " + entry.getName());
                }

                if (entry.isDirectory()) {
                    Files.createDirectories(extractedPath);
                } else {
                    Files.createDirectories(extractedPath.getParent());
                    try (OutputStream outputStream = Files.newOutputStream(extractedPath)) {
                        byte[] buffer = new byte[1024];
                        int length;
                        while ((length = zipInputStream.read(buffer)) > 0) {
                            outputStream.write(buffer, 0, length);
                        }
                    }
                }
                zipInputStream.closeEntry();
            }
            System.out.println("ZIP descomprimido con éxito en: " + targetDir);
        }
    }
}

