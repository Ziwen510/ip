package duke.command;

import duke.task.TaskList;
import duke.ui.Ui;
import duke.storage.Storage;

public class ByeCommand extends Command {
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) {
        ui.showMessage("Bye~ Have a good day!");
    }
    @Override public boolean isExit() { return true; }
    @Override public boolean modifiesStorage() { return false; }
}
