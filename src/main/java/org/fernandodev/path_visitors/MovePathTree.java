package org.fernandodev.path_visitors;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;

import static java.nio.file.FileVisitResult.CONTINUE;

public class MovePathTree extends OperationsTreeBase{

    public static void move(Path fromFile, Path toDir, CopyOption... opts) throws IOException {
        try{
            CopyDirTree.copy(fromFile, toDir, opts);
            DeleteDirTree.delete(fromFile);
            log("Directorio " + fromFile.getFileName() + " y su contenido se ha movido");
        }catch(IOException ex){
            System.err.println("Error al mover el archivo " + ex);
        }
    }
}

