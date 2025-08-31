package duke.command;

import duke.task.TaskList;
import duke.ui.Ui;
import duke.storage.Storage;
import duke.exception.DukiiException;
import duke.task.Task;

public class DeleteCommand extends Command {
    private final int index;
    public DeleteCommand(int index) { this.index = index; }
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
