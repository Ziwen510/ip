package dukii.command;

import dukii.task.TaskList;
import dukii.task.ToDo;
import dukii.ui.Ui;
import dukii.storage.Storage;
import dukii.exception.DukiiException;

public class TodoCommand extends Command {
    
    private final String description;
    
    public TodoCommand(String description) {
        this.description = description;
    }
    
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws DukiiException {
        if (description.isEmpty()) {
            throw new DukiiException("What would you like me to add to your list, sweety?");
        }
        
        tasks.addTask(new ToDo(description));
        ui.showMessage("Got it. I've added this todo:");
        System.out.println("  " + tasks.asList().get(tasks.getSize() - 1));
        ui.showMessage("Now you have " + tasks.getSize() + " task(s) in the list.");
    }
}
