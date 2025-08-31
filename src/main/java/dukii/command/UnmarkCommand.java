package dukii.command;

import dukii.task.TaskList;
import dukii.task.Task;
import dukii.ui.Ui;
import dukii.storage.Storage;
import dukii.exception.DukiiException;

/**
 * Command implementation for marking a task as pending (not completed).
 * 
 * <p>The unmark command allows users to indicate that a previously completed
 * task is now pending again. This is useful when a task needs to be redone
 * or when it was marked as done by mistake.</p>
 * 
 * <p>The command format is: "unmark &lt;index&gt;" where index is the number
 * of the task as shown in the list command output.</p>
 * 
 * @author Wang Ziwen & AIs
 * @version 1.0
 */
public class UnmarkCommand extends Command {
    
    private final int index;
    
    /**
     * Constructs a new UnmarkCommand with the specified task index.
     * 
     * @param index the index of the task to unmark (1-based indexing)
     */
    public UnmarkCommand(int index) { 
        this.index = index; 
    }
    
    /**
     * Executes the unmark command by marking the specified task as pending.
     * 
     * <p>This method validates the task index and marks the corresponding task
     * as pending (not done). It provides appropriate feedback for various
     * scenarios including empty task lists, invalid indices, and already
     * pending tasks.</p>
     * 
     * <p>If the task is already marked as pending, a message is displayed
     * without making any changes.</p>
     * 
     * @param tasks the task list containing the task to unmark
     * @param ui the user interface for displaying messages
     * @param storage the storage system (not used in this command)
     * @throws DukiiException if the task list is empty or the index is invalid
     */
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws DukiiException {
        if (tasks.isEmpty()) {
            throw new DukiiException("Oh sweety, there are no tasks to unmark! Your list is empty.");
        }
        
        if (index < 1 || index > tasks.getSize()) {
            throw new DukiiException("Oops! That task number is out of range. Please pick between 1 and " + tasks.getSize() + ".");
        }
        
        Task task = tasks.getTask(index - 1);
        if (!task.isDone()) {
            ui.showMessage("This task is not marked as done yet~");
        } else {
            task.markAsPending();
            ui.showMessage("OK, I've marked this task as not done yet:");
            System.out.println("  " + task);
        }
    }
}
