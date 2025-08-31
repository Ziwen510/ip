package dukii.task;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Represents a deadline task in the Dukii application.
 * 
 * <p>A deadline task is a task with a specific due date. It consists of a
 * description and a deadline date by which the task should be completed.
 * Deadline tasks are useful for time-sensitive work that needs to be
 * finished by a particular date.</p>
 * 
 * <p>Deadline tasks are identified by the type code "D" in storage and display.
 * The due date is displayed in a user-friendly format (e.g., "Aug 25 2025").</p>
 * 
 * @author Wang Ziwen & AIs
 * @version 1.0
 */
public class Deadline extends Task {
    
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("MMM d yyyy");
    
    private final LocalDate byDate;

    /**
     * Constructs a new Deadline task with the specified description and due date.
     * 
     * <p>The task is initially marked as pending (not done).</p>
     * 
     * @param description the description of the deadline task
     * @param byDate the due date for the task
     */
    public Deadline(String description, LocalDate byDate) {
        super(description);
        this.byDate = byDate;
    }

    /**
     * Gets the due date for this deadline task.
     * 
     * @return the LocalDate representing when the task is due
     */
    public LocalDate getByDate() {
        return byDate;
    }

    /**
     * Returns the type identifier for deadline tasks.
     * 
     * <p>This method returns "D" to identify this task as a deadline task
     * in storage and display operations.</p>
     * 
     * @return the string "D" representing the deadline task type
     */
    @Override
    public String getTaskType() {
        return "D";
    }

    /**
     * Returns a string representation of this deadline task.
     * 
     * <p>The format is: [D][Status] Description (by: MMM d yyyy), where
     * Status is "X" for done or " " for pending, and the date is formatted
     * in a user-friendly month-day-year format.</p>
     * 
     * @return a formatted string representation of the deadline task
     */
    @Override
    public String toString() {
        String display = byDate.format(DATE_FORMATTER);
        return "[" + getTaskType() + "][" + (isDone ? "X" : " ") + "] " + description + " (by: " + display + ")";
    }
}
