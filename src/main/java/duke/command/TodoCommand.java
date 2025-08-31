package duke.command;

import duke.task.TaskList;
import duke.task.ToDo;
import duke.ui.Ui;
import duke.storage.Storage;
import duke.exception.DukiiException;

public class TodoCommand extends Command {
    private final String description;
    public TodoCommand(String description) { this.description = description; }
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws DukiiException {
        if (description.isEmpty()) {
            throw new DukiiException("What would you like me to add to your list, sweety?");
        }
        tasks.asList().add(new ToDo(description));
        ui.showMessage("Got it. I've added this todo:");
        System.out.println("  " + tasks.asList().get(tasks.asList().size() - 1));
        ui.showMessage("Now you have " + tasks.asList().size() + " task(s) in the list.");
    }
}
