package org.fernandodev.file_operations;

import java.io.IOException;
import java.nio.file.CopyOption;
import java.nio.file.Files;
import java.nio.file.Path;

public class FileCopy {
    public static void copy(Path fromFile, Path toDir, CopyOption... opts) throws IOException {
        try{
            Files.copy(fromFile, toDir, opts);
            System.out.println("Archivo " + fromFile.getFileName() + " copiado");
        }catch(IOException ex){
            System.err.println("Error al copiar el archivo " + ex);
        }
    }
}