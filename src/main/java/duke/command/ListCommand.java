package duke.command;

import duke.task.TaskList;
import duke.ui.Ui;
import duke.storage.Storage;

public class ListCommand extends Command {
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) {
        if (tasks.asList().isEmpty()) {
            ui.showMessage("No task there! Enjoy your day sweety~");
            return;
        }
        ui.showMessage("Here are the tasks in your list:");
        for (int i = 0; i < tasks.asList().size(); i++) {
            System.out.println((i + 1) + "." + tasks.asList().get(i));
        }
    }
    @Override public boolean modifiesStorage() { return false; }
}
