package data_management;

import com.datamanagement.DataStorage;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

public class DataStorageMainTest {
    @Test
    void testMainRunsWithoutError() {
        // main shouldn't throw any exception even if storage is empty
        assertDoesNotThrow(() -> DataStorage.main(new String[]{}));
    }
}
