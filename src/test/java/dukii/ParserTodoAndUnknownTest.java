package dukii;

import dukii.command.TodoCommand;
import dukii.exception.DukiiException;
import dukii.parser.Parser;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ParserTodoAndUnknownTest {
    @Test
    void parsesTodoWithExtraSpaces() throws Exception {
        Parser p = new Parser();
        assertTrue(p.parse("todo    buy milk   ") instanceof TodoCommand);
    }

    @Test
    void rejectsUnknownCommand() {
        Parser p = new Parser();
        DukiiException ex = assertThrows(DukiiException.class, () -> p.parse("blargh"));
        assertEquals("Oh honey, I'm not sure what you mean by that! Could you try one of my commands?", ex.getMessage());
    }
}


