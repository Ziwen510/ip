package dukii.command;

import dukii.task.TaskList;
import dukii.task.ToDo;
import dukii.ui.Ui;
import dukii.storage.Storage;
import dukii.exception.DukiiException;

/**
 * Command implementation for adding a new todo task to the task list.
 * 
 * <p>A todo task is a simple task without any time constraints. It consists
 * only of a description and can be marked as done or pending like any other task.</p>
 * 
 * <p>The command format is: "todo &lt;description&gt;" where description is
 * the text describing what needs to be done.</p>
 * 
 * @author Wang Ziwen & AIs
 * @version 1.0
 */
public class TodoCommand extends Command {
    private final String description;
    
    /**
     * Constructs a new TodoCommand with the specified description.
     * 
     * @param description the description of the todo task
     */
    public TodoCommand(String description) { 
        this.description = description; 
    }
    
    /**
     * Executes the todo command by adding a new todo task to the task list.
     * 
     * <p>This method creates a new ToDo object with the provided description
     * and adds it to the task list. It then provides user feedback confirming
     * the task addition and displays the updated task count.</p>
     * 
     * <p>If the description is empty, a DukiiException is thrown with a
     * user-friendly error message.</p>
     * 
     * @param tasks the task list to add the todo to
     * @param ui the user interface for displaying messages
     * @param storage the storage system (not used in this command)
     * @throws DukiiException if the description is empty
     */
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws DukiiException {
        if (description.isEmpty()) {
            throw new DukiiException("What would you like me to add to your list, sweety?");
        }
        tasks.asList().add(new ToDo(description));
        ui.showMessage("Got it. I've added this todo:");
        System.out.println("  " + tasks.asList().get(tasks.asList().size() - 1));
        ui.showMessage("Now you have " + tasks.asList().size() + " task(s) in the list.");
    }
}
