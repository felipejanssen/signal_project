package data_management;

import com.datamanagement.DataStorage;
import com.datamanagement.FileDataReader;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class FileDataReaderEmmptyDirTest {
    @Test
    void testEmptyDir(@TempDir Path dir) {
        // directory exists but has no .txt files
        DataStorage storage = DataStorage.getInstance();
        // this should not throw and should leave storage empty
        assertDoesNotThrow(() -> new FileDataReader(dir.toString()).readData(storage));
        assertTrue(storage.getAllPatients().isEmpty(), "storage should be empty after reading from empty dir");
    }
}
