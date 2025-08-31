package dukii.task;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Event extends Task {
    
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("MMM d yyyy");
    
    private final LocalDate fromDate;
    private final LocalDate toDate;

    public Event(String description, LocalDate fromDate, LocalDate toDate) {
        super(description);
        this.fromDate = fromDate;
        this.toDate = toDate;
    }
    
    public LocalDate getFromDate() {
        return fromDate;
    }

    public LocalDate getToDate() {
        return toDate;
    }

    @Override
    public String getTaskType() {
        return "E";
    }
    
    @Override
    public String toString() {
        String fromDisplay = fromDate.format(DATE_FORMATTER);
        String toDisplay = toDate.format(DATE_FORMATTER);
        return "[" + getTaskType() + "][" + (isDone ? "X" : " ") + "] " + description + 
               " (from: " + fromDisplay + " to: " + toDisplay + ")";
    }
}
