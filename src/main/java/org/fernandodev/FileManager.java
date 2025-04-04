package org.fernandodev;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public class FileManager {

    static String dateFormatter (FileTime time) {
        return new SimpleDateFormat("dd-MM-yyyy HH:mm:ss")
                .format(new Date(time.toMillis()));
    };

    public static Optional<Path> findFile(String pathString){
        Path path = Paths.get(pathString);
        return Files.exists(path) && Files.isRegularFile(path) ? Optional.of(path) : Optional.empty();
    }

    public static Optional<Path> findDirectory(String pathString){
        Path path = Paths.get(pathString);
        return Files.exists(path) && Files.isDirectory(path) ? Optional.of(path) : Optional.empty();
    }

    public void printDirectoryContent(String pathString) throws IOException {
        Path path = Paths.get(pathString);

        try(DirectoryStream<Path> stream= Files.newDirectoryStream(path)){
            if(!FileManager.isDirectoryEmpty(path)){
                for(Path file: stream){
                    String fileInfo = MessageFormat.format("""
                        Nombre: {0}
                        """, file.getFileName());
                    System.out.println(fileInfo);
                }
            }else{
                System.out.println("Directorio vacío");
            }

        }catch(IOException | DirectoryIteratorException ex){
            System.err.println("El directorio no existe " + ex);
        }
    }

    public List<Path> getDirectoryContent(String pathString) throws IOException {
        Path path = Paths.get(pathString);
        List<Path> directoryContent = new ArrayList<>();

        try(DirectoryStream<Path> stream= Files.newDirectoryStream(path)){
            if(!FileManager.isDirectoryEmpty(path)){
                for(Path file: stream){
                    directoryContent.add(file);
                }
                return directoryContent;
            }else{
                System.out.println("Directorio vacío");
            }
        }catch(IOException | DirectoryIteratorException ex){
            System.err.println("El directorio no existe " + ex);
        }
        return null;
    }

    public static boolean isDirectoryEmpty(Path dir) throws IOException{
        try(DirectoryStream<Path> stream= Files.newDirectoryStream(dir)){
            return !stream.iterator().hasNext();
        }
    }

    public static void printDirectoryInfo(Path dirPath){
        if(!Files.isDirectory(dirPath)){
            System.err.println("La ruta proporcionada no es un directorio");
            return;
        }

        try{
            BasicFileAttributes basicAttrs = Files.readAttributes(dirPath, BasicFileAttributes.class);
            System.out.println("Nombre: " + dirPath.getFileName());
            System.out.println("Creado: " + dateFormatter(basicAttrs.creationTime()));
            System.out.println("Última modificación: " + dateFormatter(basicAttrs.lastModifiedTime()));
            System.out.println("Último acceso: " + dateFormatter(basicAttrs.lastAccessTime()));
            System.out.println("Tamaño: " + Files.size(dirPath) + " bytes");
            System.out.println("Tipo: Carpeta");
        }catch(IOException ex){
            System.err.println("Error al obtener la información del directorio: " + ex.getMessage());
        }
    }

    public static void printFileInfo(Path filePath){
        if(!Files.isRegularFile(filePath)){
            System.err.println("La ruta proporcionada no es un archivo");
            return;
        }

        try{
            BasicFileAttributes basicAttrs = Files.readAttributes(filePath, BasicFileAttributes.class);
            System.out.println("Nombre: " + filePath.getFileName());
            System.out.println("Creado: " + dateFormatter(basicAttrs.creationTime()));
            System.out.println("Última modificación: " + dateFormatter(basicAttrs.lastModifiedTime()));
            System.out.println("Último acceso: " + dateFormatter(basicAttrs.lastAccessTime()));
            System.out.println("Tamaño: " + Files.size(filePath) + " bytes");
            System.out.println("Tipo: Archivo");
        }catch(IOException ex){
            System.err.println("Error al obtener la información del archivo: " + ex.getMessage());
        }
    }
}
