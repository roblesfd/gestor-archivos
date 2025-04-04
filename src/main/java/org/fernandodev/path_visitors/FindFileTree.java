package org.fernandodev.path_visitors;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Optional;

import static java.nio.file.FileVisitResult.CONTINUE;
import static java.nio.file.FileVisitResult.TERMINATE;

public class FindFileTree extends OperationsTreeBase{
    private final Path basePath;

    public FindFileTree(Path path){
         basePath = path;
    }

    public Optional<Path> searchFile(String fileName){
        FindFileVisitor visitor = new FindFileVisitor(fileName);

        try{
            Files.walkFileTree(basePath, visitor);
        }catch (IOException ex){
            System.err.println("Error al buscar el archivo " + ex);
        }
        return visitor.getFoundFile();
    }

    private static class FindFileVisitor extends SimpleFileVisitor<Path> {
        private final String fileNameToFind;
        Optional<Path> foundFile =  Optional.empty();

        public FindFileVisitor(String fileName){
            this.fileNameToFind = fileName;
        }

        @Override
        public FileVisitResult visitFile(Path file, BasicFileAttributes attrs){
            if(file.getFileName().toString().equals(fileNameToFind)){
                foundFile = Optional.of(file);
                log("Archivo encontrado " + file);
                return TERMINATE;
            }
            return CONTINUE;
        }

        public Optional<Path> getFoundFile() {
            return foundFile;
        }
    }
}

