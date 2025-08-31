package dukii.command;

import java.time.LocalDate;
import dukii.task.TaskList;
import dukii.task.Event;
import dukii.ui.Ui;
import dukii.storage.Storage;

public class EventCommand extends Command {
    private final String description;
    private final LocalDate fromDate;
    private final LocalDate toDate;
    public EventCommand(String description, LocalDate fromDate, LocalDate toDate) {
        this.description = description;
        this.fromDate = fromDate;
        this.toDate = toDate;
    }
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) {
        tasks.asList().add(new Event(description, fromDate, toDate));
        ui.showMessage("Got it. I've added this event:");
        System.out.println("  " + tasks.asList().get(tasks.asList().size() - 1));
        ui.showMessage("Now you have " + tasks.asList().size() + " task(s) in the list.");
    }
}
