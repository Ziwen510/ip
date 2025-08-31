package dukii.parser;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import dukii.exception.DukiiException;
import dukii.command.*;

public class Parser {
    
    private static final String TODO_PREFIX = "todo ";
    private static final String DEADLINE_PREFIX = "deadline ";
    private static final String EVENT_PREFIX = "event ";
    private static final String MARK_PREFIX = "mark ";
    private static final String UNMARK_PREFIX = "unmark ";
    private static final String DELETE_PREFIX = "delete ";
    private static final String BYE_COMMAND = "bye";
    private static final String LIST_COMMAND = "list";
    
    public Command parse(String input) throws DukiiException {
        String trimmed = input.trim();
        
        if (trimmed.equals(BYE_COMMAND)) {
            return new ByeCommand();
        } else if (trimmed.equals(LIST_COMMAND)) {
            return new ListCommand();
        } else if (trimmed.startsWith(TODO_PREFIX)) {
            return parseTodoCommand(trimmed);
        } else if (trimmed.startsWith(DEADLINE_PREFIX)) {
            return parseDeadlineCommand(trimmed);
        } else if (trimmed.startsWith(EVENT_PREFIX)) {
            return parseEventCommand(trimmed);
        } else if (trimmed.startsWith(MARK_PREFIX)) {
            return parseMarkCommand(trimmed);
        } else if (trimmed.startsWith(UNMARK_PREFIX)) {
            return parseUnmarkCommand(trimmed);
        } else if (trimmed.startsWith(DELETE_PREFIX)) {
            return parseDeleteCommand(trimmed);
        }
        
        throw new DukiiException("Oh honey, I'm not sure what you mean by that! Could you try one of my commands?");
    }
    
    private Command parseTodoCommand(String input) throws DukiiException {
        String description = input.substring(TODO_PREFIX.length()).trim();
        if (description.isEmpty()) {
            throw new DukiiException("What would you like me to add to your list, sweety?");
        }
        return new TodoCommand(description);
    }
    
    private Command parseDeadlineCommand(String input) throws DukiiException {
        if (!input.contains(" by ")) {
            throw new DukiiException("Honey, I need to know when this is due! Please use: deadline <description> by <time>");
        }
        
        String[] parts = input.substring(DEADLINE_PREFIX.length()).trim().split(" by ");
        if (parts.length != 2 || parts[0].isEmpty() || parts[1].isEmpty()) {
            throw new DukiiException("Sweetie, I need both the task and when it's due! Try: deadline <description> by <time>");
        }
        
        LocalDate dueDate = parseDate(parts[1].trim());
        return new DeadlineCommand(parts[0].trim(), dueDate);
    }
    
    private Command parseEventCommand(String input) throws DukiiException {
        if (!input.contains(" from ")) {
            throw new DukiiException("Sweetie, for events I need to know when they start and end! Try: event <description> from <start_time> to <end_time>");
        }
        
        String[] parts = input.substring(EVENT_PREFIX.length()).trim().split(" from ");
        if (parts.length != 2 || parts[0].isEmpty() || parts[1].isEmpty()) {
            throw new DukiiException("Hmm, I'm a bit confused! Please tell me: event <description> from <start_time> to <end_time>");
        }
        
        if (!parts[1].contains(" to ")) {
            throw new DukiiException("I need both start and end times, honey! Use: event <description> from <start_time> to <end_time>");
        }
        
        String[] timeParts = parts[1].trim().split(" to ");
        if (timeParts.length != 2 || timeParts[0].isEmpty() || timeParts[1].isEmpty()) {
            throw new DukiiException("Oops! I need the complete event details: event <description> from <start_time> to <end_time>");
        }
        
        LocalDate fromDate = parseDate(timeParts[0].trim());
        LocalDate toDate = parseDate(timeParts[1].trim());
        
        return new EventCommand(parts[0].trim(), fromDate, toDate);
    }
    
    private Command parseMarkCommand(String input) throws DukiiException {
        int index = parseIndex(input.substring(MARK_PREFIX.length()).trim());
        return new MarkCommand(index);
    }
    
    private Command parseUnmarkCommand(String input) throws DukiiException {
        int index = parseIndex(input.substring(UNMARK_PREFIX.length()).trim());
        return new UnmarkCommand(index);
    }
    
    private Command parseDeleteCommand(String input) throws DukiiException {
        int index = parseIndex(input.substring(DELETE_PREFIX.length()).trim());
        return new DeleteCommand(index);
    }
    
    private LocalDate parseDate(String dateString) throws DukiiException {
        try {
            return LocalDate.parse(dateString);
        } catch (DateTimeParseException e) {
            throw new DukiiException("Honey, please use date format yyyy-MM-dd!");
        }
    }
    
    private int parseIndex(String indexString) throws DukiiException {
        try {
            return Integer.parseInt(indexString);
        } catch (NumberFormatException e) {
            throw new DukiiException("Sweetie, I need a real number! Please try again.");
        }
    }
}


