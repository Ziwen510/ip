package duke.task;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Deadline extends Task {
    private LocalDate byDate;

    public Deadline(String description, LocalDate byDate) {
        super(description);
        this.byDate = byDate;
    }

    public LocalDate getByDate() {
        return this.byDate;
    }

    @Override
    public String getTaskType() {
        return "D";
    }

    @Override
    public String toString() {
        String display = byDate.format(DateTimeFormatter.ofPattern("MMM d yyyy"));
        return "[" + getTaskType() + "][" + (isDone ? "X" : " ") + "] " + description + " (by: " + display + ")";
    }
}
