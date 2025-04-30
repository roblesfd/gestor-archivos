package org.fernandodev.path_visitors;

import java.io.*;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import static java.nio.file.FileVisitResult.CONTINUE;

public class DirectoryCompressorTree {

    public static void compressToZip(Path sourceDir, Path zipFile) throws IOException {
        if(!Files.exists(sourceDir) && !sourceDir.toFile().isDirectory()){
            throw new IOException("El directorio no existe: " + sourceDir);
        }

        Files.createDirectories(zipFile.getParent());

        try(ZipOutputStream zipOutputStream = new ZipOutputStream(new FileOutputStream(zipFile.toFile()))){
            Files.walkFileTree(sourceDir, new DirectoryCompressorVisitor(sourceDir, zipOutputStream));
            System.out.println("Directorio comprimido con éxito en: " + zipFile);
        }
    }

    private static final class DirectoryCompressorVisitor extends SimpleFileVisitor<Path>{
        private final Path sourceDir;
        private final ZipOutputStream zipOutputStream;

        DirectoryCompressorVisitor(Path srcDir, ZipOutputStream zOutStream){
            this.sourceDir= srcDir;
            this.zipOutputStream = zOutStream;
        }

        @Override
        public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException{
            Path relativePath = sourceDir.relativize(file);
            ZipEntry zipEntry = new ZipEntry(relativePath.toString().replace("\\", "/"));
            zipOutputStream.putNextEntry(zipEntry);

            try(InputStream stream = Files.newInputStream(file)){
                byte[] buffer = new byte[1024];
                int length;
                while((length = stream.read(buffer)) > 0){
                    zipOutputStream.write(buffer, 0, length);
                }
            }
            zipOutputStream.closeEntry();
            return CONTINUE;
        }

        @Override
        public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException{
            Path relativePath = sourceDir.relativize(dir);
            if(!relativePath.toString().isEmpty()){
                ZipEntry entry = new ZipEntry(relativePath.toString().replace("\\", "/"));
                zipOutputStream.putNextEntry(entry);
                zipOutputStream.closeEntry();
            }
            return CONTINUE;
        }
    }
}
