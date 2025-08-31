package dukii;

import dukii.exception.DukiiException;
import dukii.parser.Parser;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ParserMarkUnmarkDeleteIndexTest {
    @Test
    void mark_rejectsNonNumeric() {
        Parser p = new Parser();
        DukiiException ex = assertThrows(DukiiException.class, () -> p.parse("mark abc"));
        assertEquals("Sweetie, I need a real number to mark the task! Please try again.", ex.getMessage());
    }

    @Test
    void unmark_rejectsNonNumeric() {
        Parser p = new Parser();
        DukiiException ex = assertThrows(DukiiException.class, () -> p.parse("unmark xyz"));
        assertEquals("Honey, I need a proper number to unmark the task! Please try again.", ex.getMessage());
    }

    @Test
    void delete_rejectsNonNumeric() {
        Parser p = new Parser();
        DukiiException ex = assertThrows(DukiiException.class, () -> p.parse("delete one"));
        assertEquals("Honey, I need a real number to delete the task! Please try again.", ex.getMessage());
    }
}


