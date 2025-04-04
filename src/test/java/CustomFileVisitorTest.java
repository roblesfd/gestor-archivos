import org.fernandodev.CustomFileVisitor;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.fernandodev.CustomFileVisitor.Action.SHOW;
import static org.junit.jupiter.api.Assertions.*;

public class CustomFileVisitorTest {
    CustomFileVisitor visitor;
    ByteArrayOutputStream outputStream;
    PrintStream originalErr = System.err;
    PrintStream originalOut = System.out;

    @BeforeEach
    public void setup(){
        visitor = new CustomFileVisitor(SHOW);
        outputStream = new ByteArrayOutputStream();
    }

    @AfterEach
    public void tearDown(){
        System.setOut(originalOut);
        System.setErr(originalErr);
    }

    @Test
    public void testVisitFilesPrintsName(@TempDir Path path) throws IOException {
        Files.createFile(path.resolve("file1.txt"));
        Files.createFile(path.resolve("file2.txt"));

        System.setOut(new PrintStream(outputStream));

        Files.walkFileTree(path, visitor);

        String output = outputStream.toString();
        assertTrue(output.contains("Archivo: file1.txt"), "Debe de mostrar el nombre del archivo");
        assertTrue(output.contains("Archivo: file2.txt"), "Debe de mostrar el nombre del archivo");
    }

    @Test
    public void testVisitFileFailedHandlesError() throws IOException {
        Path path = Paths.get("/ruta/invalida");

        System.setErr(new PrintStream(outputStream));

        Files.walkFileTree(path, visitor);

        String output = outputStream.toString();
        assertTrue(output.contains("Ocurrió un error"), "Debe de mostrar el texto del error del catch");
    }

}
