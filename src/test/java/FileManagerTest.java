import org.fernandodev.FileManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;


public class FileManagerTest {
    FileManager manager;
    ByteArrayOutputStream outputStream;
    PrintStream originalErr = System.err;
    PrintStream originalOut = System.out;

    @BeforeEach
    public void setup(){
        manager = new FileManager();
        outputStream = new ByteArrayOutputStream();
    }

    @AfterEach
    public void tearDown(){
        System.setErr(originalErr);
        System.setOut(originalOut);
    }

    abstract class BaseDirectoryTests {
        abstract void getTestedMethod(String path) throws IOException;

        @Test
        void testEmptyDirectory(@TempDir Path path) throws IOException {

            System.setOut(new PrintStream(outputStream));
            getTestedMethod(path.toString());
            String output = outputStream.toString().trim();

            assertEquals("Directorio vacío", output, "El mensaje de directorio vacío no coincide.");
        }

        @Test
        void testNonExistentDirectory() throws IOException {
            System.setErr(new PrintStream(outputStream));

            getTestedMethod("ruta/no/existe");
            String errorOutput = outputStream.toString().trim();

            assertTrue(errorOutput.contains("El directorio no existe"), "Debe imprimir un mensaje de error en System.err para directorios inexistentes.");
        }

    }

    @Nested
    class TestPrintDirectoryContent extends BaseDirectoryTests{
        @Override
        void getTestedMethod(String path) throws IOException {
            manager.printDirectoryContent(path);
        }

        @Test
        public void testListFilesInExistingDirectory(@TempDir Path path) throws IOException {
            Files.createFile(path.resolve("file1.txt"));
            Files.createFile(path.resolve("file2.txt"));

            try(DirectoryStream<Path> stream= Files.newDirectoryStream(path)){
                int fileCount=0;
                for (Path file : stream) {
                    fileCount++;
                }
                assertEquals( 2, fileCount,"Debe haber 2 archivos en el directorio");
            }
        }

        @Test
        void testPrintsExistingContentInDirectory(@TempDir Path dir) throws IOException{

            Files.createFile(dir.resolve("file1.txt"));
            System.setOut(new PrintStream(outputStream));

            manager.printDirectoryContent(dir.toString());
            String printMessage = outputStream.toString().trim();

            assertTrue(printMessage.contains("Nombre: "));
        }
    }

    @Nested
    class TestGetDirectoryContent extends BaseDirectoryTests{
        @Override
        void getTestedMethod(String path) throws IOException {
            manager.getDirectoryContent(path);
        }

        @Test
        public void testReturnsAPathObject(@TempDir Path path) throws IOException {
            Files.createFile(path.resolve("file1.txt"));
            List<Path> result =  manager.getDirectoryContent(path.toString());
            assertInstanceOf(Path.class, result.getFirst());
        }

        @Test
        public void testReturnsAtLeastOneChild(@TempDir Path path) throws IOException {
            Files.createFile(path.resolve("file1.txt"));
            List<Path> result =  manager.getDirectoryContent(path.toString());
            assertEquals(1, result.size(), "Debe devolver el tamaño de la lista de contenidos");
        }
    }

    @Nested
    class TestFindFile{
        @Test
        public void testFindExistentFile(@TempDir Path path) throws IOException{
            Path file = path.resolve("file1.txt");
            Files.createFile(file);

            Optional<Path> resultado = FileManager.findFile(file.toString());
            assertTrue(resultado.isPresent(), "El archivo deberia de existir");
        }

        @Test
        public void testFindNonExistentFile(@TempDir Path path) throws IOException{
            Path file = path.resolve("file1.txt");

            Optional<Path> resultado = FileManager.findFile(file.toString());
            assertFalse(resultado.isPresent(), "El archivo no deberia de existir");
        }
    }
}
