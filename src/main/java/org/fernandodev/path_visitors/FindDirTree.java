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

public class FindDirTree extends OperationsTreeBase{
    private final Path basePath;

    public FindDirTree(Path path){
         basePath = path;
    }

    public Optional<Path> searchDirectory(String directoryName){
        FindDirectoryVisitor visitor = new FindDirectoryVisitor(directoryName);

        try{
            Files.walkFileTree(basePath, visitor);
        }catch (IOException ex){
            System.err.println("Error al buscar el directorio " + ex);
        }
        return visitor.getFoundFile();
    }

    private static class FindDirectoryVisitor extends SimpleFileVisitor<Path> {
        private final String directoryNameToFind;
        Optional<Path> foundDirectory =  Optional.empty();

        public FindDirectoryVisitor(String directoryName){
            this.directoryNameToFind = directoryName;
        }

        @Override
        public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs){
            if(dir.getFileName().toString().equalsIgnoreCase(directoryNameToFind)){
                foundDirectory = Optional.of(dir);
                log("Directorio encontrado " + dir);
                return TERMINATE;
            }
            return CONTINUE;
        }

        public Optional<Path> getFoundFile() {
            return foundDirectory;
        }
    }
}

