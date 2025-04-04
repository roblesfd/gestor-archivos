package org.fernandodev.path_visitors;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;

import static java.nio.file.FileVisitResult.CONTINUE;

public class CopyDirTree extends OperationsTreeBase{

    public static void copy(Path fromDir, Path toDir, CopyOption... opts) throws IOException {
        if(shouldCopy(fromDir, toDir)){
            Files.walkFileTree(fromDir, new CopyPathVisitor(fromDir, toDir, opts));
            log("Elementos copiados.");
        }
    }

    private static boolean shouldCopy(Path fromDir, Path toDir){
        boolean fromDirExistsAndIsDir = fromDir.toFile().isDirectory();
        boolean toIsBelowFrom = toDir.toAbsolutePath().startsWith(fromDir.toAbsolutePath());
        boolean result = fromDirExistsAndIsDir && !toIsBelowFrom;

        if(!result){
            log("No se puede copiar " + fromDir + " a " + toDir);
            log(fromDirExistsAndIsDir, toIsBelowFrom);
        }
        return result;
    }

    private static final class CopyPathVisitor extends SimpleFileVisitor<Path> {
        private final Path sourceRoot;
        private final Path targetRoot;
        private CopyOption[] options;

        public CopyPathVisitor(Path sourceRoot, Path targetRoot, CopyOption... opts) {
            this.sourceRoot = sourceRoot;
            this.targetRoot = targetRoot;
            this.options = opts;
        }

        @Override
        public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
            log("Creando el directorio " + dir);
            Files.createDirectories(targetRoot.resolve(sourceRoot.relativize(dir)));
            return CONTINUE;
        }

        @Override
        public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
            log("Copiando archivo: " + file);
            Files.copy(file, targetRoot.resolve(sourceRoot.relativize(file)), StandardCopyOption.REPLACE_EXISTING);
            return CONTINUE;
        }
    }
}


