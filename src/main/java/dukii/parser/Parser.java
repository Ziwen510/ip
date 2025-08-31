package dukii.parser;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import dukii.exception.DukiiException;
import dukii.command.*;

/**
 * Parser class responsible for interpreting user input and converting it into
 * executable commands.
 * 
 * <p>The parser handles various command formats and validates input before
 * creating the appropriate command objects. It supports the following commands:</p>
 * <ul>
 *   <li>bye - exits the application</li>
 *   <li>list - displays all tasks</li>
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
    /**
     * Parses the user input string and returns the corresponding command object.
     * 
     * <p>This method analyzes the input string to determine the command type and
     * extracts any necessary parameters. It performs validation on the input and
     * throws DukiiException with descriptive error messages if the input is invalid.</p>
     * 
     * <p>The method handles various edge cases such as missing parameters, invalid
     * date formats, and malformed command syntax.</p>
     * 
     * @param input the raw user input string
     * @return a Command object representing the parsed command
     * @throws DukiiException if the input cannot be parsed or is invalid
     */
    public Command parse(String input) throws DukiiException {
        String trimmed = input.trim();
        if (trimmed.equals("bye")) {
            return new ByeCommand();
        } else if (trimmed.equals("list")) {
            return new ListCommand();
        } else if (trimmed.startsWith("todo ")) {
            String desc = trimmed.substring(5).trim();
            return new TodoCommand(desc);
        } else if (trimmed.startsWith("deadline ")) {
            if (!trimmed.contains(" by ")) {
                throw new DukiiException("Honey, I need to know when this is due! Please use: deadline <description> by <time>");
            }
            String[] parts = trimmed.substring(9).trim().split(" by ");
            if (parts.length != 2 || parts[0].isEmpty() || parts[1].isEmpty()) {
                throw new DukiiException("Sweetie, I need both the task and when it's due! Try: deadline <description> by <time>");
            }
            LocalDate by;
            try {
                by = LocalDate.parse(parts[1].trim());
            } catch (DateTimeParseException e) {
                throw new DukiiException("Honey, please use date format yyyy-MM-dd! Try: deadline <description> by <yyyy-MM-dd>");
            }
            return new DeadlineCommand(parts[0].trim(), by);
        } else if (trimmed.startsWith("event ")) {
            if (!trimmed.contains(" from ")) {
                throw new DukiiException("Sweetie, for events I need to know when they start and end! Try: event <description> from <start_time> to <end_time>");
            }
            String[] parts = trimmed.substring(6).trim().split(" from ");
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
            LocalDate fromDate;
            LocalDate toDate;
            try {
                fromDate = LocalDate.parse(timeParts[0].trim());
                toDate = LocalDate.parse(timeParts[1].trim());
            } catch (DateTimeParseException e) {
                throw new DukiiException("Honey, please use date format yyyy-MM-dd! Try: event <description> from <yyyy-MM-dd> to <yyyy-MM-dd>");
            }
            return new EventCommand(parts[0].trim(), fromDate, toDate);
        } else if (trimmed.startsWith("mark ")) {
            int idx;
            try {
                idx = Integer.parseInt(trimmed.substring(5).trim());
            } catch (NumberFormatException e) {
                throw new DukiiException("Sweetie, I need a real number to mark the task! Please try again.");
            }
            return new MarkCommand(idx);
        } else if (trimmed.startsWith("unmark ")) {
            int idx;
            try {
                idx = Integer.parseInt(trimmed.substring(7).trim());
            } catch (NumberFormatException e) {
                throw new DukiiException("Honey, I need a proper number to unmark the task! Please try again.");
            }
            return new UnmarkCommand(idx);
        } else if (trimmed.startsWith("delete ")) {
            int idx;
            try {
                idx = Integer.parseInt(trimmed.substring(7).trim());
            } catch (NumberFormatException e) {
                throw new DukiiException("Honey, I need a real number to delete the task! Please try again.");
            }
            return new DeleteCommand(idx);
        }
        throw new DukiiException("Oh honey, I'm not sure what you mean by that! Could you try one of my commands?");
    }
}


