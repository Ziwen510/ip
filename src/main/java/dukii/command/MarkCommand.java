package dukii.command;

import dukii.task.TaskList;
import dukii.task.Task;
import dukii.ui.Ui;
import dukii.storage.Storage;
import dukii.exception.DukiiException;

/**
 * Command implementation for marking a task as completed.
 * 
 * <p>The mark command allows users to indicate that a specific task has been
 * completed. The task is identified by its position in the task list, which
 * can be viewed using the list command.</p>
 * 
 * <p>The command format is: "mark &lt;index&gt;" where index is the number
 * of the task as shown in the list command output.</p>
 * 
 * @author Wang Ziwen & AIs
 * @version 1.0
 */
public class MarkCommand extends Command {
    
    private final int index;
    
    /**
     * Constructs a new MarkCommand with the specified task index.
     * 
     * @param index the index of the task to mark (1-based indexing)
     */
    public MarkCommand(int index) { 
        this.index = index; 
    }
    
    /**
     * Executes the mark command by marking the specified task as completed.
     * 
     * <p>This method validates the task index and marks the corresponding task
     * as done. It provides appropriate feedback for various scenarios including
     * empty task lists, invalid indices, and already completed tasks.</p>
     * 
     * <p>If the task is already marked as done, a message is displayed without
     * making any changes.</p>
     * 
     * @param tasks the task list containing the task to mark
     * @param ui the user interface for displaying messages
     * @param storage the storage system (not used in this command)
     * @throws DukiiException if the task list is empty or the index is invalid
     */
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws DukiiException {
        if (tasks.isEmpty()) {
            throw new DukiiException("Oh sweety, there are no tasks to mark! Your list is empty.");
        }
        
        if (index < 1 || index > tasks.getSize()) {
            throw new DukiiException("Oh dear! That task number doesn't exist. Please choose between 1 and " + tasks.getSize() + ".");
        }
        
        Task task = tasks.getTask(index - 1);
        if (task.isDone()) {
            ui.showMessage("This task is already marked as done~");
        } else {
            task.markAsDone();
            ui.showMessage("Good job sweety! I've marked this task as done:");
            System.out.println("  " + task);
        }
    }
}
