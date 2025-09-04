package dukii.command;

import dukii.task.TaskList;
import dukii.ui.Ui;
import dukii.storage.Storage;

/**
 * Command implementation for displaying all tasks in the task list.
 * 
 * <p>The list command shows all currently stored tasks with their completion
 * status and descriptions. Tasks are numbered sequentially starting from 1
 * for easy reference in other commands like mark, unmark, and delete.</p>
 * 
 * <p>The command format is simply "list" with no additional parameters.</p>
 * 
 * @author Wang Ziwen & AIs
 * @version 1.0
 */
public class ListCommand extends Command {
    /**
     * Executes the list command by displaying all tasks in the task list.
     * 
     * <p>This method checks if the task list is empty and provides appropriate
     * feedback. If tasks exist, it displays each task with a sequential number,
     * showing the task type, completion status, and description.</p>
     * 
     * @param tasks the task list to display
     * @param ui the user interface for displaying messages
     * @param storage the storage system (not used in this command)
     */
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) {
        if (tasks.isEmpty()) {
            ui.showMessage("No task there! Enjoy your day sweety~");
            return;
        }
        
        ui.showMessage("Here are the tasks in your list:");
        for (int i = 0; i < tasks.getSize(); i++) {
            ui.showMessage((i + 1) + "." + tasks.asList().get(i));
        }
    }
    
    /**
     * Indicates that this command does not modify the task storage.
     * 
     * <p>Since listing tasks is a read-only operation, this method returns
     * false to prevent unnecessary save operations.</p>
     * 
     * @return false since this command doesn't modify storage
     */
    @Override 
    public boolean modifiesStorage() { 
        return false; 
    }
}
