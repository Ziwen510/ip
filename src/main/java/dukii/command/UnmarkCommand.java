package dukii.command;

import dukii.task.TaskList;
import dukii.task.Task;
import dukii.ui.Ui;
import dukii.storage.Storage;
import dukii.exception.DukiiException;

public class UnmarkCommand extends Command {
    private final int index;
    public UnmarkCommand(int index) { this.index = index; }
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws DukiiException {
        if (tasks.asList().isEmpty()) {
            throw new DukiiException("Oh sweety, there are no tasks to unmark! Your list is empty.");
        }
        if (index < 1 || index > tasks.asList().size()) {
            throw new DukiiException("Oops! That task number is out of range. Please pick between 1 and " + tasks.asList().size() + ".");
        }
        Task task = tasks.asList().get(index - 1);
        if (!task.isDone()) {
            ui.showMessage("This task is not marked as done yet~");
        } else {
            task.markAsPending();
            ui.showMessage("OK, I've marked this task as not done yet:");
            System.out.println("  " + task);
        }
    }
}
