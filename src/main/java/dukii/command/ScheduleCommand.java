package dukii.command;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import dukii.storage.Storage;
import dukii.task.Deadline;
import dukii.task.Event;
import dukii.task.Task;
import dukii.task.TaskList;
import dukii.ui.Ui;

/**
 * Command implementation for listing tasks scheduled on a specific date.
 *
 * <p>Shows deadlines due on the date and events whose date range contains the
 * given date. The command does not modify storage.</p>
 */
public class ScheduleCommand extends Command {
    private final LocalDate date;

    /**
     * Constructs a new ScheduleCommand for the given date.
     *
     * @param date the date to query the schedule for
     */
    public ScheduleCommand(LocalDate date) {
        this.date = date;
    }

    /**
     * Displays tasks that are due or occur on the specified date.
     */
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) {
        List<Task> all = tasks.asList();
        List<Task> matching = new ArrayList<>();

        for (int i = 0; i < all.size(); i++) {
            Task t = all.get(i);
            if (t instanceof Deadline) {
                if (((Deadline) t).getByDate().isEqual(date)) {
                    matching.add(t);
                }
            } else if (t instanceof Event) {
                Event e = (Event) t;
                if ((date.isEqual(e.getFromDate()) || date.isAfter(e.getFromDate()))
                        && (date.isEqual(e.getToDate()) || date.isBefore(e.getToDate()))) {
                    matching.add(t);
                }
            }
        }

        ui.showMessage("Schedule for " + date + ":");
        if (matching.isEmpty()) {
            ui.showMessage("No scheduled tasks on this date.");
            return;
        }

        for (int i = 0; i < matching.size(); i++) {
            Task task = matching.get(i);
            int originalIndex = all.indexOf(task) + 1;
            ui.showMessage(originalIndex + "." + task.toString());
        }
    }

    @Override
    public boolean modifiesStorage() {
        return false;
    }
}


