package org.fernandodev.path_visitors;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Objects;

import static java.nio.file.FileVisitResult.CONTINUE;

public final class DeleteDirTree extends OperationsTreeBase{

    public static void delete(Path dir) throws IOException {
        if(shouldDelete(dir)){
            Files.walkFileTree(dir, new DeleteDirVisitor(dir));
            log("Operación completada");
        }
    }

    private static boolean shouldDelete(Path dir){
        return dir.toFile().exists() && dir.toFile().isDirectory();
    }

    private static final class DeleteDirVisitor extends SimpleFileVisitor<Path>{
        Path dir;

        public DeleteDirVisitor(Path dir){
            this.dir = dir;
        }

        @Override
        public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException{
            Files.delete(file);
            log("Archivo eliminado: " + file.getFileName());
            return CONTINUE;
        }

        @Override
        public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
            Files.delete(dir);
            log("Directorio eliminado: " + dir.getFileName());
            return CONTINUE;
        }
    }

}
