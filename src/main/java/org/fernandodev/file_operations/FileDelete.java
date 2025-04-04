package org.fernandodev.file_operations;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class FileDelete {
    public static void delete(Path file) throws IOException {
        try{
            Files.delete(file);
            System.out.println("Archivo " + file.getFileName() + " eliminado");
        }catch(IOException ex){
            System.err.println("Error al eliminar el archivo " + ex);
        }
    }
}