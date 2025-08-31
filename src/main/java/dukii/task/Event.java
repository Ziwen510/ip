package dukii.task;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Represents an event task in the Dukii application.
 * 
 * <p>An event task is a task with a specific time period. It consists of a
 * description, a start date, and an end date defining when the event occurs.
 * Event tasks are useful for activities that span multiple days or have
 * a defined duration.</p>
 * 
 * <p>Event tasks are identified by the type code "E" in storage and display.
 * Both start and end dates are displayed in a user-friendly format (e.g., "Aug 25 2025").</p>
 * 
 * @author Wang Ziwen & AIs
 * @version 1.0
 */
public class Event extends Task {
    private LocalDate fromDate;
    private LocalDate toDate;

    /**
     * Constructs a new Event task with the specified description and date range.
     * 
     * <p>The task is initially marked as pending (not done).</p>
     * 
     * @param description the description of the event task
     * @param fromDate the start date of the event
     * @param toDate the end date of the event
     */
    public Event(String description, LocalDate fromDate, LocalDate toDate) {
        super(description);
        this.fromDate = fromDate;
        this.toDate = toDate;
    }
    
    /**
     * Gets the start date for this event task.
     * 
     * @return the LocalDate representing when the event starts
     */
    public LocalDate getFromDate() {
        return this.fromDate;
    }

    /**
     * Gets the end date for this event task.
     * 
     * @return the LocalDate representing when the event ends
     */
    public LocalDate getToDate() {
        return this.toDate;
    }

    /**
     * Returns the type identifier for event tasks.
     * 
     * <p>This method returns "E" to identify this task as an event task
     * in storage and display operations.</p>
     * 
     * @return the string "E" representing the event task type
     */
    @Override
    public String getTaskType() {
        return "E";
    }
    
    /**
     * Returns a string representation of this event task.
     * 
     * <p>The format is: [E][Status] Description (from: MMM d yyyy to: MMM d yyyy), where
     * Status is "X" for done or " " for pending, and both dates are formatted
     * in a user-friendly month-day-year format.</p>
     * 
     * @return a formatted string representation of the event task
     */
    @Override
    public String toString() {
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("MMM d yyyy");
        String fromDisplay = fromDate.format(fmt);
        String toDisplay = toDate.format(fmt);
        return "[" + getTaskType() + "][" + (isDone ? "X" : " ") + "] " + description + " (from: " + fromDisplay + " to: " + toDisplay + ")";
    }
}
