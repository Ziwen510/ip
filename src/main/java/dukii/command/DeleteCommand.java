package dukii.command;

import dukii.task.TaskList;
import dukii.ui.Ui;
import dukii.storage.Storage;
import dukii.exception.DukiiException;
import dukii.task.Task;

public class DeleteCommand extends Command {
    
    private final int index;
    
    public DeleteCommand(int index) {
        this.index = index;
    }
    
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws DukiiException {
        if (tasks.isEmpty()) {
            throw new DukiiException("Oh sweety, there are no tasks to delete! Your list is empty.");
        }
        
        if (index < 1 || index > tasks.getSize()) {
            throw new DukiiException("Oh no! That task number doesn't exist. Please choose between 1 and " + tasks.getSize() + ".");
        }
        
        Task deletedTask = tasks.getTask(index - 1);
        tasks.removeTask(index - 1);
        ui.showMessage("Noted. I've removed this task:");
        System.out.println("  " + deletedTask);
        ui.showMessage("Now you have " + tasks.getSize() + " task(s) in the list.");
    }
}
