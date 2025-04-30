package org.fernandodev;

import org.fernandodev.file_operations.FileDecompressZip;
import org.fernandodev.file_operations.FileEncryptor;
import org.fernandodev.path_visitors.*;

import javax.crypto.SecretKey;
import java.io.IOException;
import java.nio.file.*;

public class Main {
    public static void main(String[] args) throws Exception {
        CopyOption[] CAN_OVERWRITE = {
            StandardCopyOption.REPLACE_EXISTING,
            StandardCopyOption.COPY_ATTRIBUTES,
            LinkOption.NOFOLLOW_LINKS
        };

        Path basePath = Paths.get("C:\\Users\\ferna\\Documents\\Plan de estudio\\Java\\Libros");
        Path file1 = Paths.get("C:\\Users\\ferna\\Documents\\Plan de estudio\\Java\\Libros\\pillo.txt");
        Path file2 = Paths.get("C:\\Users\\ferna\\Documents\\Plan de estudio\\Java\\Libros\\moved\\pillo.txt");
        Path fileEncrypted = Paths.get("C:\\Users\\ferna\\Documents\\Plan de estudio\\Java\\Libros\\encrypted");
        Path directory1 = Paths.get("C:\\Users\\ferna\\Documents\\Plan de estudio\\Java\\Libros\\pedro");
        Path directory2 = Paths.get("C:\\Users\\ferna\\Documents\\Plan de estudio\\Java\\Libros\\pancho");
        Path dirCompressedFile = Paths.get("C:\\Users\\ferna\\Documents\\Plan de estudio\\Java\\Libros\\compress\\compressedfile.zip");
        Path dirCompressedDirectory = Paths.get("C:\\Users\\ferna\\Documents\\Plan de estudio\\Java\\Libros\\compress\\compresseddirectory.zip");
        Path dirDecompressedDirectory = Paths.get("C:\\Users\\ferna\\Documents\\Plan de estudio\\Java\\Libros\\decompressed\\decompressedFile.txt");
        Path dirDecryptedFile = Paths.get("C:\\Users\\ferna\\Documents\\Plan de estudio\\Java\\Libros\\decrypted\\decryptedfile.txt");


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
//        Optional<Path> foundDirectory = dirFinder.searchDirectory("dirtosearch");

        //COMPRESS FILE
//        FileCompressZip.compressFileToZip(file1, dirCompressedFile.toString());
        //COMPRESS DIRECTORY
//        DirectoryCompressorTree.compressToZip(directory1, dirCompressedDirectory.toString());

        //DECOMPRESS DIRECTORY
//        DirectoryDecompressorTree.extractZip(directory1, directory2);
        //DECOMPRESS FILE
//        FileDecompressZip.extractSingleFile(dirCompressedFile, dirDecompressedDirectory);

        //ENCRYPT FILE
        SecretKey secretKey = FileEncryptor.generateKey();
//        FileEncryptor.encryptFile(file1, fileEncrypted, secretKey);
        //DECRYPT FILE
//        FileEncryptor.decryptFile(fileEncrypted, dirDecryptedFile, secretKey);

        //ENCRYPT DIRECTORY
//        DirectoryEncryptorTree.encrypt(directory1, dirCompressedDirectory, secretKey);
        //DECRYPT DIRECTORY

    }
}