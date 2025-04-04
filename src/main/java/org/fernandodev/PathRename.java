package org.fernandodev;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

public class PathRename {
    public static boolean rename(Path oldPath, String newName){
        Path newNamePath = oldPath.resolveSibling(newName);
        try{
            Files.move(oldPath, newNamePath, StandardCopyOption.REPLACE_EXISTING);
            System.out.println("Elemento " + oldPath.getFileName() + " renombrado a " + newNamePath.getFileName());
            return true;
        }catch(IOException ex){
            System.err.println("Error al renombrar: " + ex.getMessage());
            return false;
        }
    }
}
