package dukii.task;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Event extends Task {
    private LocalDate fromDate;
    private LocalDate toDate;

    public Event(String description, LocalDate fromDate, LocalDate toDate) {
        super(description);
        this.fromDate = fromDate;
        this.toDate = toDate;
    }
    
    public LocalDate getFromDate() {
        return this.fromDate;
    }

    public LocalDate getToDate() {
        return this.toDate;
    }

    @Override
    public String getTaskType() {
        return "E";
    }
    
    @Override
    public String toString() {
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("MMM d yyyy");
        String fromDisplay = fromDate.format(fmt);
        String toDisplay = toDate.format(fmt);
        return "[" + getTaskType() + "][" + (isDone ? "X" : " ") + "] " + description + " (from: " + fromDisplay + " to: " + toDisplay + ")";
    }
}
