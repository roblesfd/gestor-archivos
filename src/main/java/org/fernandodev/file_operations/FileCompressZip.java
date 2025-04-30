package org.fernandodev.file_operations;
import java.io.*;
import java.nio.file.*;
import java.util.zip.*;

public class FileCompressZip {

    public static void compressFileToZip(Path filePath, String zipFilePath) {
        try {
            if (!Files.exists(filePath)) {
                throw new IOException("El archivo no existe: " + filePath);
            }

            try (ZipOutputStream zipOutputStream = new ZipOutputStream(new FileOutputStream(zipFilePath));
                 FileInputStream fileInputStream = new FileInputStream(filePath.toFile())){

            ZipEntry zipEntry = new ZipEntry(filePath.getFileName().toString());
            zipOutputStream.putNextEntry(zipEntry);

            byte[] buffer = new byte[1024];
            int length;
            while ((length = fileInputStream.read(buffer)) > 0) {
                zipOutputStream.write(buffer, 0, length);
            }

            System.out.println("Archivo " + filePath.getFileName() + " comprimido");

            zipOutputStream.closeEntry();
            }
        }catch(IOException ex){
            System.err.println("Error al comprimir el archivo: " + ex.getMessage());
        }
    }
}
