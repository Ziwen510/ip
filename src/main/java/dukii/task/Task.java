package dukii.task;

/**
 * Abstract base class representing a task in the Dukii application.
 * 
 * <p>A task is a unit of work that can be tracked and managed. Each task has
 * a description and a completion status. Tasks can be marked as done or pending,
 * and they provide a string representation for display purposes.</p>
 * 
 * <p>This class serves as the foundation for different types of tasks such as
 * todos, deadlines, and events. Concrete implementations should override the
 * {@code getTaskType} method to provide their specific type identifier.</p>
 * 
 * @author Wang Ziwen & AIs
 * @version 1.0
 */
public abstract class Task {
    
    private String description;
    private boolean isDone;
    
    /**
     * Constructs a new task with the given description.
     * 
     * <p>The task is initially marked as not done (pending).</p>
     * 
     * @param description the description of the task
     */
    public Task(String description) {
        this.description = description;
        this.isDone = false;
    }
    
    /**
     * Gets the description of this task.
     * 
     * @return the task description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Marks this task as completed.
     * 
     * <p>This method changes the completion status of the task to done.</p>
     */
    public void markAsDone() {
        isDone = true;
    }
    
    /**
     * Marks this task as pending (not completed).
     * 
     * <p>This method changes the completion status of the task to pending.</p>
     */
    public void markAsPending() {
        isDone = false;
    }
    
    /**
     * Checks if this task is completed.
     * 
     * @return true if the task is done, false if it is pending
     */
    public boolean isDone() {
        return isDone;
    }
    
    /**
     * Gets the type identifier for this task.
     * 
     * <p>This method should be implemented by concrete task classes to return
     * a unique identifier representing the task type (e.g., "T" for todo,
     * "D" for deadline, "E" for event).</p>
     * 
     * @return a string representing the task type
     */
    public abstract String getTaskType();
    
    /**
     * Returns a string representation of this task.
     * 
     * <p>The format is: [Type][Status] Description, where Type is the task type,
     * Status is "X" for done or " " for pending, and Description is the task description.</p>
     * 
     * @return a formatted string representation of the task
     */
    @Override
    public String toString() {
        return "[" + getTaskType() + "][" + (isDone ? "X" : " ") + "] " + description;
    }
}
