package decorator_tests;

import com.alerts.decorator.Alertable;
import com.alerts.decorator.PriorityAlertDecorator;
import com.alerts.decorator.RepeatedAlertDecorator;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class DecoratorPatternTest {
    // backup of the original System.out
    private final PrintStream originalOut = System.out;

    @Test
    void testPriorityAlertDecorator() {
        Alertable alert = new Alertable() {
            @Override public void show() { System.out.println("Base alert showing"); }
            @Override public String getPatientId() { return "123"; }
            @Override public String getCondition() { return "TestCondition"; }
            @Override public long getTimestamp() { return 999L; }
        };

        // capture console output
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));

        Alertable highPriority = new PriorityAlertDecorator(alert, "HIGH");
        highPriority.show();

        // restore System.out
        System.setOut(originalOut);

        String output = out.toString();

        assertTrue(output.contains("[HIGH]"), "Priority tag missing");
        assertTrue(output.contains("Base alert showing"), "Base show() not called");
    }

    @Test
    void testRepeatedAlertDecorator() {
        Alertable alert = new Alertable() {
            @Override public void show() { System.out.println("X"); }
            @Override public String getPatientId() { return ""; }
            @Override public String getCondition() { return ""; }
            @Override public long getTimestamp()   { return 0L; }
        };

        // capture output
        ByteArrayOutputStream outCapture = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outCapture));

        // wrap alert to repeat 3 times
        Alertable repeater = new RepeatedAlertDecorator(alert, 3);
        repeater.show(); // prints X three times

        // restore System.out
        System.setOut(originalOut);

        String[] lines = outCapture.toString().split(System.lineSeparator());
        // expect exactly 3 lines of "X"
        assertEquals(3, lines.length, "Should print three lines");
        for (String line : lines) {
            assertEquals("X", line, "Each line should be 'X'");
        }
    }
}
