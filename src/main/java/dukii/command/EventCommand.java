package dukii.command;

import java.time.LocalDate;
import dukii.task.TaskList;
import dukii.task.Event;
import dukii.ui.Ui;
import dukii.storage.Storage;

/**
 * Command implementation for adding a new event task to the task list.
 * 
 * <p>An event task is a task with a specific time period. It consists of a
 * description, a start date, and an end date defining when the event occurs.</p>
 * 
 * <p>The command format is: "event &lt;description&gt; from &lt;start_date&gt; to &lt;end_date&gt;"
 * where description is the text describing the event and dates are in yyyy-MM-dd format.</p>
 * 
 * @author Wang Ziwen & AIs
 * @version 1.0
 */
public class EventCommand extends Command {
    
    private final String description;
    private final LocalDate fromDate;
    private final LocalDate toDate;
    
    /**
     * Constructs a new EventCommand with the specified description and date range.
     * 
     * @param description the description of the event task
     * @param fromDate the start date of the event
     * @param toDate the end date of the event
     */
    public EventCommand(String description, LocalDate fromDate, LocalDate toDate) {
        this.description = description;
        this.fromDate = fromDate;
        this.toDate = toDate;
    }
    
    /**
     * Executes the event command by adding a new event task to the task list.
     * 
     * <p>This method creates a new Event object with the provided description
     * and date range, then adds it to the task list. It provides user feedback
     * confirming the task addition and displays the updated task count.</p>
     * 
     * @param tasks the task list to add the event to
     * @param ui the user interface for displaying messages
     * @param storage the storage system (not used in this command)
     */
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) {
        tasks.addTask(new Event(description, fromDate, toDate));
        ui.showMessage("Got it. I've added this event:");
        ui.showMessage("  " + tasks.asList().get(tasks.getSize() - 1));
        ui.showMessage("Now you have " + tasks.getSize() + " task(s) in the list.");
    }
}
