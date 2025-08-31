package dukii;

import dukii.command.DeadlineCommand;
import dukii.exception.DukiiException;
import dukii.parser.Parser;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ParserDeadlineTest {
    @Test
    void parsesDeadlineCommand() throws Exception {
        Parser p = new Parser();
        assertTrue(p.parse("deadline submit report by 2025-12-31") instanceof DeadlineCommand);
    }

    @Test
    void rejectsNonIsoDeadline() {
        Parser p = new Parser();
        DukiiException ex = assertThrows(DukiiException.class,
                () -> p.parse("deadline submit report by 31-12-2025"));
        assertEquals("Honey, please use date format yyyy-MM-dd! Try: deadline <description> by <yyyy-MM-dd>", ex.getMessage());
    }

    @Test
    void rejectsMissingBy() {
        Parser p = new Parser();
        DukiiException ex = assertThrows(DukiiException.class,
                () -> p.parse("deadline submit report"));
        assertEquals("Honey, I need to know when this is due! Please use: deadline <description> by <time>", ex.getMessage());
    }
}


