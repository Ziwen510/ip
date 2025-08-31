package dukii.command;

import dukii.task.TaskList;
import dukii.ui.Ui;
import dukii.storage.Storage;
import dukii.exception.DukiiException;
import dukii.task.Task;

/**
 * Command implementation for removing a task from the task list.
 * 
 * <p>The delete command allows users to permanently remove a specific task
 * from their task list. Once deleted, a task cannot be recovered. The task
 * is identified by its position in the task list, which can be viewed using
 * the list command.</p>
 * 
 * <p>The command format is: "delete &lt;index&gt;" where index is the number
 * of the task as shown in the list command output.</p>
 * 
 * @author Wang Ziwen & AIs
 * @version 1.0
 */
public class DeleteCommand extends Command {
    private final int index;
    
    /**
     * Constructs a new DeleteCommand with the specified task index.
     * 
     * @param index the index of the task to delete (1-based indexing)
     */
    public DeleteCommand(int index) { 
        this.index = index; 
    }
    
    /**
     * Executes the delete command by removing the specified task from the task list.
     * 
     * <p>This method validates the task index, removes the corresponding task,
     * and provides user feedback. It displays the deleted task details and
     * updates the user on the new task count.</p>
     * 
     * <p>The deletion is permanent and cannot be undone.</p>
     * 
     * @param tasks the task list containing the task to delete
     * @param ui the user interface for displaying messages
     * @param storage the storage system (not used in this command)
     * @throws DukiiException if the task list is empty or the index is invalid
     */
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws DukiiException {
        if (tasks.asList().isEmpty()) {
            throw new DukiiException("Oh sweety, there are no tasks to delete! Your list is empty.");
        }
        if (index < 1 || index > tasks.asList().size()) {
            throw new DukiiException("Oh no! That task number doesn't exist. Please choose between 1 and " + tasks.asList().size() + ".");
        }
        Task deletedTask = tasks.asList().get(index - 1);
        tasks.asList().remove(index - 1);
        ui.showMessage("Noted. I've removed this task:");
        System.out.println("  " + deletedTask);
        ui.showMessage("Now you have " + tasks.asList().size() + " task(s) in the list.");
    }
}
