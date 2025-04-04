package org.fernandodev.file_operations;

import java.io.IOException;
import java.nio.file.CopyOption;
import java.nio.file.Path;

public class FileMove {
    public static void move(Path fromFile, Path toDir, CopyOption... opts) throws IOException {
        try{
            FileCopy.copy(fromFile, toDir, opts);
            FileDelete.delete(fromFile);
            System.out.println("Archivo " + fromFile.getFileName() + " movido");
        }catch(IOException ex){
            System.err.println("Error al mover el archivo " + ex);
        }
    }
}