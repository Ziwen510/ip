package duke.command;

import java.time.LocalDate;
import duke.task.TaskList;
import duke.task.Deadline;
import duke.ui.Ui;
import duke.storage.Storage;

public class DeadlineCommand extends Command {
    private final String description;
    private final LocalDate byDate;
    public DeadlineCommand(String description, LocalDate byDate) {
        this.description = description;
        this.byDate = byDate;
    }
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) {
        tasks.asList().add(new Deadline(description, byDate));
        ui.showMessage("Got it. I've added this task:");
        System.out.println("  " + tasks.asList().get(tasks.asList().size() - 1));
        ui.showMessage("Now you have " + tasks.asList().size() + " task(s) in the list.");
    }
}
