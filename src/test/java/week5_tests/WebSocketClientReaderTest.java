package week5_tests;

import com.datamanagement.DataStorage;
import com.datamanagement.PatientRecord;
import com.datamanagement.WebSocketClientReader;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URI;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class WebSocketClientReaderTest {

    // a tiny subclass so that connect() doesn't actually try to open sockets
    private static class TestReader extends WebSocketClientReader {
        public TestReader(URI uri) {
            super(uri);
        }
        @Override
        public void connect() {
            // do nothing
        }
    }

    private DataStorage storage;
    private WebSocketClientReader reader;

    @BeforeEach
    void setup() throws IOException {
        storage = DataStorage.getInstance();
        storage.clearAll();               // wipe any old data
        reader = new TestReader(URI.create("ws://localhost:9999"));
        reader.start(storage);            // sets the storage field
    }

    @Test
    void testOnMessage_validInputStoresOneRecord() {
        // this is a normal comma message, no percent sign
        reader.onMessage("5,1612345678000,HeartRate,72.0");

        // pull out exactly that one record
        List<PatientRecord> recs = storage.getRecords(
                5, 1612345678000L, 1612345678000L
        );
        assertEquals(1, recs.size(), "should have stored exactly 1 record");

        PatientRecord r = recs.get(0);
        assertEquals(5, r.getPatientId(), "wrong patient id");
        assertEquals("HeartRate", r.getRecordType(), "wrong record type");
        assertEquals(72.0, r.getMeasurementValue(), 0.0001, "wrong value");
        assertEquals(1612345678000L, r.getTimestamp(), "wrong timestamp");
    }

    @Test
    void testOnMessage_withPercentStripsPercentSign() {
        // message ends with a percent sign
        reader.onMessage("2,1000,Temp,37.5%");

        List<PatientRecord> recs = storage.getRecords(2, 1000L, 1000L);
        assertEquals(1, recs.size(), "percent input should still store");
        assertEquals(37.5, recs.get(0).getMeasurementValue(), 0.0001,
                "percent sign should be removed");
    }

    @Test
    void testOnMessage_malformedTooFewPartsIsIgnored() {
        // only 3 parts, should just return
        reader.onMessage("1,2000,OnlyThree");
        List<PatientRecord> recs = storage.getRecords(1, 0, Long.MAX_VALUE);
        assertTrue(recs.isEmpty(), "bad message should not store anything");
    }

    @Test
    void testOnMessage_badNumberFormatIsCaught() {
        // pid is not an int, should hit the catch block
        reader.onMessage("x,2000,Label,50.0");
        List<PatientRecord> recs = storage.getAllPatients().isEmpty()
                ? List.of()
                : storage.getRecords(0, 0, Long.MAX_VALUE);
        assertTrue(recs.isEmpty(), "parse errors should not store");
    }

    @Test
    void testMultipleMessages_accumulateRecords() {
        // send two messages for same patient at diff times
        reader.onMessage("3,500,ECG,10");
        reader.onMessage("3,600,ECG,12");

        List<PatientRecord> recs = storage.getRecords(3, 0, 1000);
        assertEquals(2, recs.size(), "should have both records stored");
    }
}
