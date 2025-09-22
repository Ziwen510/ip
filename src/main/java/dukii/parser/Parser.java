package dukii.parser;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import dukii.exception.DukiiException;
import dukii.command.ByeCommand;
import dukii.command.Command;
import dukii.command.DeadlineCommand;
import dukii.command.DeleteCommand;
import dukii.command.EventCommand;
import dukii.command.FindCommand;
import dukii.command.ListCommand;
import dukii.command.MarkCommand;
import dukii.command.ScheduleCommand;
import dukii.command.TodoCommand;
import dukii.command.UnmarkCommand;

/**
 * Parser class responsible for interpreting user input and converting it into
 * executable commands.
 * 
 * <p>The parser handles various command formats and validates input before
 * creating the appropriate command objects. It supports the following commands:</p>
 * <ul>
 *   <li>bye - exits the application</li>
 *   <li>list - displays all tasks</li>
 *   <li>find &lt;keyword&gt; - searches for tasks containing the keyword</li>
 *   <li>todo &lt;description&gt; - creates a new todo task</li>
 *   <li>deadline &lt;description&gt; by &lt;date&gt; - creates a deadline task</li>
 *   <li>event &lt;description&gt; from &lt;start_date&gt; to &lt;end_date&gt; - creates an event task</li>
 *   <li>mark &lt;index&gt; - marks a task as done</li>
 *   <li>unmark &lt;index&gt; - marks a task as pending</li>
 *   <li>delete &lt;index&gt; - removes a task</li>
 * </ul>
 * 
 * <p>Date formats should be in yyyy-MM-dd format (e.g., 2025-08-25).</p>
 * 
 * @author Wang Ziwen & AIs
 * @version 1.0
 */
public class Parser {
    
    private static final String TODO_PREFIX = "todo ";
    private static final String DEADLINE_PREFIX = "deadline ";
    private static final String EVENT_PREFIX = "event ";
    private static final String MARK_PREFIX = "mark ";
    private static final String UNMARK_PREFIX = "unmark ";
    private static final String DELETE_PREFIX = "delete ";
    private static final String FIND_PREFIX = "find ";
    private static final String SCHEDULE_PREFIX = "schedule ";
    private static final String BYE_COMMAND = "bye";
    private static final String LIST_COMMAND = "list";
    
    /**
     * Parses the raw user input into a concrete command.
     *
     * @param input the raw user input string
     * @return the concrete {@code Command} represented by the input
     * @throws DukiiException if the input is invalid or the command is unknown
     */
    public Command parse(final String input) throws DukiiException {
        assert input != null;
        String trimmed = input.trim();
        
        if (trimmed.equalsIgnoreCase(BYE_COMMAND)) {
            return new ByeCommand();
        } else if (trimmed.equals(LIST_COMMAND)) {
            return new ListCommand();
        } else if (trimmed.equals("find")) {
            throw new DukiiException("Sweetie, please tell me what you're looking for! Use: find <keyword>");
        } else if (trimmed.startsWith(FIND_PREFIX)) {
            return parseFindCommand(trimmed);
        } else if (trimmed.equals("schedule")) {
            throw new DukiiException("Sweetie, please tell me which date to view! Use: schedule <yyyy-MM-dd>");
        } else if (trimmed.startsWith(SCHEDULE_PREFIX)) {
            return parseScheduleCommand(trimmed);
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
    
    private Command parseTodoCommand(final String input) throws DukiiException {
        String description = input.substring(TODO_PREFIX.length()).trim();
        if (description.isEmpty()) {
            throw new DukiiException("What would you like me to add to your list, sweety?");
        }
        return new TodoCommand(description);
    }
    
    private Command parseDeadlineCommand(final String input) throws DukiiException {
        if (!input.contains(" by ")) {
            throw new DukiiException("Honey, I need to know when this is due! Please use: deadline <description> by <time>");
        }
        
        String[] parts = input.substring(DEADLINE_PREFIX.length()).trim().split(" by ");
        assert parts != null;
        if (parts.length != 2 || parts[0].isEmpty() || parts[1].isEmpty()) {
            throw new DukiiException("Sweetie, I need both the task and when it's due! Try: deadline <description> by <time>");
        }
        
        LocalDate dueDate;
        try {
            dueDate = LocalDate.parse(parts[1].trim());
        } catch (DateTimeParseException e) {
            throw new DukiiException("Honey, please use date format yyyy-MM-dd! Try: deadline <description> by <yyyy-MM-dd>");
        }
        return new DeadlineCommand(parts[0].trim(), dueDate);
    }
    
    private Command parseEventCommand(final String input) throws DukiiException {
        if (!input.contains(" from ")) {
            throw new DukiiException("Sweetie, for events I need to know when they start and end! Try: event <description> from <start_time> to <end_time>");
        }
        
        String[] parts = input.substring(EVENT_PREFIX.length()).trim().split(" from ");
        assert parts != null;
        if (parts.length != 2 || parts[0].isEmpty() || parts[1].isEmpty()) {
            throw new DukiiException("Hmm, I'm a bit confused! Please tell me: event <description> from <start_time> to <end_time>");
        }
        
        if (!parts[1].contains(" to ")) {
            throw new DukiiException("I need both start and end times, honey! Use: event <description> from <start_time> to <end_time>");
        }
        
        String[] timeParts = parts[1].trim().split(" to ");
        assert timeParts != null;
        if (timeParts.length != 2 || timeParts[0].isEmpty() || timeParts[1].isEmpty()) {
            throw new DukiiException("Oops! I need the complete event details: event <description> from <start_time> to <end_time>");
        }
        
        LocalDate fromDate;
        LocalDate toDate;
        try {
            fromDate = LocalDate.parse(timeParts[0].trim());
            toDate = LocalDate.parse(timeParts[1].trim());
        } catch (DateTimeParseException e) {
            throw new DukiiException("Honey, please use date format yyyy-MM-dd! Try: event <description> from <yyyy-MM-dd> to <yyyy-MM-dd>");
        }
        
        return new EventCommand(parts[0].trim(), fromDate, toDate);
    }
    
    private Command parseMarkCommand(final String input) throws DukiiException {
        String indexString = input.substring(MARK_PREFIX.length()).trim();
        int index;
        try {
            index = Integer.parseInt(indexString);
        } catch (NumberFormatException e) {
            throw new DukiiException("Sweetie, I need a real number to mark the task! Please try again.");
        }
        return new MarkCommand(index);
    }
    
    private Command parseUnmarkCommand(final String input) throws DukiiException {
        String indexString = input.substring(UNMARK_PREFIX.length()).trim();
        int index;
        try {
            index = Integer.parseInt(indexString);
        } catch (NumberFormatException e) {
            throw new DukiiException("Honey, I need a proper number to unmark the task! Please try again.");
        }
        return new UnmarkCommand(index);
    }
    
    private Command parseDeleteCommand(final String input) throws DukiiException {
        String indexString = input.substring(DELETE_PREFIX.length()).trim();
        int index;
        try {
            index = Integer.parseInt(indexString);
        } catch (NumberFormatException e) {
            throw new DukiiException("Sweetie, I need a real number to delete the task! Please try again.");
        }
        return new DeleteCommand(index);
    }
    
    private Command parseFindCommand(final String input) throws DukiiException {
        String keyword = input.substring(FIND_PREFIX.length()).trim();
        if (keyword.isEmpty()) {
            throw new DukiiException("Sweetie, please tell me what you're looking for! Use: find <keyword>");
        }
        return new FindCommand(keyword);
    }
    
    private LocalDate parseDate(final String dateString) throws DukiiException {
        try {
            return LocalDate.parse(dateString);
        } catch (DateTimeParseException e) {
            throw new DukiiException("Honey, please use date format yyyy-MM-dd!");
        }
    }

    private Command parseScheduleCommand(final String input) throws DukiiException {
        String dateString = input.substring(SCHEDULE_PREFIX.length()).trim();
        if (dateString.isEmpty()) {
            throw new DukiiException("Sweetie, please tell me which date to view! Use: schedule <yyyy-MM-dd>");
        }
        LocalDate date = parseDate(dateString);
        return new ScheduleCommand(date);
    }
    
    private int parseIndex(final String indexString) throws DukiiException {
        try {
            return Integer.parseInt(indexString);
        } catch (NumberFormatException e) {
            throw new DukiiException("Sweetie, I need a real number! Please try again.");
        }
    }
}


