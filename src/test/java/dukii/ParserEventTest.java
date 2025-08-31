package dukii;

import dukii.command.EventCommand;
import dukii.exception.DukiiException;
import dukii.parser.Parser;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ParserEventTest {
    @Test
    void parsesEventCommand() throws Exception {
        Parser p = new Parser();
        assertTrue(p.parse("event party from 2025-01-01 to 2025-01-02") instanceof EventCommand);
    }

    @Test
    void rejectsMissingTo() {
        Parser p = new Parser();
        DukiiException ex = assertThrows(DukiiException.class,
                () -> p.parse("event party from 2025-01-01"));
        assertEquals("I need both start and end times, honey! Use: event <description> from <start_time> to <end_time>", ex.getMessage());
    }

    @Test
    void rejectsNonIsoEventDates() {
        Parser p = new Parser();
        DukiiException ex = assertThrows(DukiiException.class,
                () -> p.parse("event party from 01/01/2025 to 02/01/2025"));
        assertEquals("Honey, please use date format yyyy-MM-dd! Try: event <description> from <yyyy-MM-dd> to <yyyy-MM-dd>", ex.getMessage());
    }
}


