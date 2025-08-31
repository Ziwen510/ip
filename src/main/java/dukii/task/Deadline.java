package dukii.task;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Deadline extends Task {
    
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("MMM d yyyy");
    
    private final LocalDate byDate;

    public Deadline(String description, LocalDate byDate) {
        super(description);
        this.byDate = byDate;
    }

    public LocalDate getByDate() {
        return byDate;
    }

    @Override
    public String getTaskType() {
        return "D";
    }

    @Override
    public String toString() {
        String display = byDate.format(DATE_FORMATTER);
        return "[" + getTaskType() + "][" + (isDone ? "X" : " ") + "] " + description + " (by: " + display + ")";
    }
}
