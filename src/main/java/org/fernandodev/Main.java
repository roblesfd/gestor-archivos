package org.fernandodev;

import org.fernandodev.path_visitors.CopyDirTree;
import org.fernandodev.path_visitors.DeleteDirTree;
import org.fernandodev.path_visitors.FindDirTree;
import org.fernandodev.path_visitors.FindFileTree;

import java.io.IOException;
import java.nio.file.*;
import java.util.Optional;

public class Main {
    public static void main(String[] args) throws IOException {
        CopyOption[] CAN_OVERWRITE = {
            StandardCopyOption.REPLACE_EXISTING,
            StandardCopyOption.COPY_ATTRIBUTES,
            LinkOption.NOFOLLOW_LINKS
        };

        Path basePath = Paths.get("C:\\Users\\ferna\\Documents\\Plan de estudio\\Java\\Libros");
        Path file1 = Paths.get("C:\\Users\\ferna\\Documents\\Plan de estudio\\Java\\Libros\\pillo.txt");
        Path file2 = Paths.get("C:\\Users\\ferna\\Documents\\Plan de estudio\\Java\\Libros\\moved\\pillo.txt");
        Path directory1 = Paths.get("C:\\Users\\ferna\\Documents\\Plan de estudio\\Java\\Libros\\pedro");
        Path directory2 = Paths.get("C:\\Users\\ferna\\Documents\\Plan de estudio\\Java\\Libros\\pancho");
        FindFileTree fileFinder = new FindFileTree(basePath);
        FindDirTree dirFinder = new FindDirTree(basePath);

        //COPY
        //copiar un directorio y su contenido
//        CopyDirTree.copy(directory1, directory2, CAN_OVERWRITE);
        //copiar un archivo
//        FileCopy.copy(file1, file2, CAN_OVERWRITE);

        //DELETE
        //elimina un directorio y su contenido
//        DeleteDirTree.delete(directory1);
        //elimina un archivo
//        FileDelete.delete(file1);

        //RENAME
        //renombra un archivo
//        PathRename.rename(file1, "archivo1.txt");
        //renombra un directorio
//        PathRename.rename(directory1, "renombrado");

        //MOVE
        //mover un archivo
//        FileMove.move(file1, Paths.get("C:\\Users\\ferna\\Documents\\Plan de estudio\\Java\\Libros\\moved\\pillo.txt"));
        //mover un directorio
//        MovePathTree.move(directory2, Paths.get("C:\\Users\\ferna\\Documents\\Plan de estudio\\Java\\Libros\\moved"));

        //PRINT DIR INFO
//        FileManager.printDirectoryInfo(directory1);
        //PRINT FILE INFO
//        FileManager.printFileInfo(file1);

        //FIND
        //FIND FILE ON DIR
//        Optional<Path> foundFile = fileFinder.searchFile("filetofoun.txt");
        //FIND DIR
        Optional<Path> foundDirectory = dirFinder.searchDirectory("dirtosearch");
    }
}