package dukii;

import dukii.command.FindCommand;
import dukii.command.Command;
import dukii.parser.Parser;
import dukii.exception.DukiiException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ParserFindTest {
    private Parser parser = new Parser();

    @Test
    void parse_findCommandWithKeyword_returnsFindCommand() throws DukiiException {
        Command command = parser.parse("find groceries");
        assertTrue(command instanceof FindCommand);
        
        FindCommand findCommand = (FindCommand) command;
        // We can't directly access the keyword field, but we can test the behavior
        // by executing it and checking the output
    }

    @Test
    void parse_findCommandWithMultipleWords_returnsFindCommand() throws DukiiException {
        Command command = parser.parse("find buy groceries");
        assertTrue(command instanceof FindCommand);
    }

    @Test
    void parse_findCommandWithLeadingSpaces_returnsFindCommand() throws DukiiException {
        Command command = parser.parse("  find groceries");
        assertTrue(command instanceof FindCommand);
    }

    @Test
    void parse_findCommandWithTrailingSpaces_returnsFindCommand() throws DukiiException {
        Command command = parser.parse("find groceries  ");
        assertTrue(command instanceof FindCommand);
    }

    @Test
    void parse_findCommandWithEmptyKeyword_throwsException() {
        DukiiException exception = assertThrows(DukiiException.class, () -> {
            parser.parse("find ");
        });
        
        assertEquals("Sweetie, please tell me what you're looking for! Use: find <keyword>", 
                    exception.getMessage());
    }

    @Test
    void parse_findCommandWithOnlySpacesAfterFind_throwsException() {
        DukiiException exception = assertThrows(DukiiException.class, () -> {
            parser.parse("find    ");
        });
        
        assertEquals("Sweetie, please tell me what you're looking for! Use: find <keyword>", 
                    exception.getMessage());
    }

    @Test
    void parse_findCommandWithSpecialCharacters_returnsFindCommand() throws DukiiException {
        Command command = parser.parse("find groceries & milk");
        assertTrue(command instanceof FindCommand);
    }

    @Test
    void parse_findCommandWithNumbers_returnsFindCommand() throws DukiiException {
        Command command = parser.parse("find task 123");
        assertTrue(command instanceof FindCommand);
    }

    @Test
    void parse_findCommandCaseInsensitive_throwsException() {
        // The parser only recognizes lowercase "find", so uppercase should fail
        DukiiException exception = assertThrows(DukiiException.class, () -> {
            parser.parse("FIND groceries");
        });
        
        assertEquals("Oh honey, I'm not sure what you mean by that! Could you try one of my commands?", 
                    exception.getMessage());
    }

    @Test
    void parse_findCommandMixedCase_throwsException() {
        // The parser only recognizes lowercase "find", so mixed case should fail
        DukiiException exception = assertThrows(DukiiException.class, () -> {
            parser.parse("Find groceries");
        });
        
        assertEquals("Oh honey, I'm not sure what you mean by that! Could you try one of my commands?", 
                    exception.getMessage());
    }
}
