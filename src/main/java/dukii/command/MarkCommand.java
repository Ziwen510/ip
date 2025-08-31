package dukii.command;

import dukii.task.TaskList;
import dukii.task.Task;
import dukii.ui.Ui;
import dukii.storage.Storage;
import dukii.exception.DukiiException;

public class MarkCommand extends Command {
    
    private final int index;
    
    public MarkCommand(int index) {
        this.index = index;
    }
    
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
