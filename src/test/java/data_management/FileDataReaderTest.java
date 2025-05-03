package data_management;

import com.datamanagement.DataStorage;
import com.datamanagement.FileDataReader;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FileDataReaderTest {
    @Test
    void testReadData(@TempDir Path directory) throws IOException {
        // create a temp file with one record
        Path f = directory.resolve("a.txt");
        String line = "Patient ID: 1, Timestamp: 100, Label: Y, Data: 3.5";
        Files.write(f, line.getBytes());

        // read data from the temp directory
        DataStorage storage = DataStorage.getInstance();
        new FileDataReader(directory.toString()).readData(storage);

        // verify the record was added
        assertEquals(1, storage.getRecords(1, 0L, 200L).size());
    }
}
