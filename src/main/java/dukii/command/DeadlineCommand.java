package dukii.command;

import java.time.LocalDate;
import dukii.task.TaskList;
import dukii.task.Deadline;
import dukii.ui.Ui;
import dukii.storage.Storage;

/**
 * Command implementation for adding a new deadline task to the task list.
 * 
 * <p>A deadline task is a task with a specific due date. It consists of a
 * description and a deadline date by which the task should be completed.</p>
 * 
 * <p>The command format is: "deadline &lt;description&gt; by &lt;date&gt;" where
 * description is the text describing what needs to be done and date is in
 * yyyy-MM-dd format.</p>
 * 
 * @author Wang Ziwen & AIs
 * @version 1.0
 */
public class DeadlineCommand extends Command {
    private final String description;
    private final LocalDate byDate;
    
    /**
     * Constructs a new DeadlineCommand with the specified description and due date.
     * 
     * @param description the description of the deadline task
     * @param byDate the due date for the task
     */
    public DeadlineCommand(String description, LocalDate byDate) {
        this.description = description;
        this.byDate = byDate;
    }
    
    /**
     * Executes the deadline command by adding a new deadline task to the task list.
     * 
     * <p>This method creates a new Deadline object with the provided description
     * and due date, then adds it to the task list. It provides user feedback
     * confirming the task addition and displays the updated task count.</p>
     * 
     * @param tasks the task list to add the deadline to
     * @param ui the user interface for displaying messages
     * @param storage the storage system (not used in this command)
     */
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) {
        tasks.asList().add(new Deadline(description, byDate));
        ui.showMessage("Got it. I've added this task:");
        System.out.println("  " + tasks.asList().get(tasks.asList().size() - 1));
        ui.showMessage("Now you have " + tasks.asList().size() + " task(s) in the list.");
    }
}
