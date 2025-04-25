package data_management;

import com.datamanagement.DataStorage;
import com.datamanagement.FileDataReader;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class FileDataReaderManualAlertTest {
    @Test
    void testManualAlertPrints(@TempDir Path directory) throws IOException {
        // make a file with an Alert line
        Path f = directory.resolve("manual_alert.txt");
        String line = "Patient ID: 7, Timestamp: 1234, Label: Alert, Data: triggered";
        Files.write(f, line.getBytes());

        // capture console output
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PrintStream old = System.out;
        System.setOut(new PrintStream(out));

        // read data from directory
        new FileDataReader(directory.toString()).readData(new DataStorage());

        // restore console
        System.setOut(old);
        String printed = out.toString();
        // verify the alert line as printed
        assertTrue(printed.contains("Patient 7 Alert: triggered"));
    }
}
