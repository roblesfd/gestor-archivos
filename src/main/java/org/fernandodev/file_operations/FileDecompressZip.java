package org.fernandodev.file_operations;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class FileDecompressZip {
    public static void extractSingleFile(Path zipFilePath, Path outputPath) throws IOException {
        if(!Files.exists(zipFilePath) || !zipFilePath.toString().endsWith(".zip")){
            throw new IOException(("Archivo ZIP inválido o no existe " + zipFilePath));
        }

        Files.createDirectories((outputPath.getParent()));

        try(ZipInputStream zipInputStream = new ZipInputStream(new FileInputStream(zipFilePath.toFile()))){
            ZipEntry entry = zipInputStream.getNextEntry();
            if(entry != null){
                Files.createDirectories(outputPath.getParent());
                try(OutputStream outputStream = Files.newOutputStream((outputPath))){
                    byte[] buffer = new byte[1024];
                    int length;
                    while((length = zipInputStream.read(buffer)) > 0){
                        outputStream.write(buffer,0,length);
                    }
                    System.out.println("Archivo extraído con éxito en: " + outputPath);
                }catch(IOException ex){
                    System.err.println("Error al extraer el archivo ");
                }
            }
        }
    }
}
