package dukii.task;

/**
 * Represents a todo task in the Dukii application.
 * 
 * <p>A todo task is a simple task without any time constraints. It consists
 * only of a description and can be marked as done or pending like any other task.
 * Todo tasks are the most basic type of task and are suitable for simple
 * reminders or tasks that don't have specific deadlines.</p>
 * 
 * <p>Todo tasks are identified by the type code "T" in storage and display.</p>
 * 
 * @author Wang Ziwen & AIs
 * @version 1.0
 */
public class ToDo extends Task {
    /**
     * Constructs a new ToDo task with the specified description.
     * 
     * <p>The task is initially marked as pending (not done).</p>
     * 
     * @param description the description of the todo task
     */
    public ToDo(String description) {
        super(description);
    }
    
    /**
     * Returns the type identifier for todo tasks.
     * 
     * <p>This method returns "T" to identify this task as a todo task
     * in storage and display operations.</p>
     * 
     * @return the string "T" representing the todo task type
     */
    @Override
    public String getTaskType() {
        return "T";
    }
}
