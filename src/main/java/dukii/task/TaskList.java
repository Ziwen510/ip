package dukii.task;

import java.util.ArrayList;

/**
 * A container class that manages a collection of tasks.
 * 
 * <p>TaskList provides a wrapper around an ArrayList of tasks, offering
 * a clean interface for task management operations. It serves as a bridge
 * between the command layer and the underlying task storage.</p>
 * 
 * <p>This class is designed to be simple and focused, primarily providing
 * access to the underlying task collection. Future enhancements could include
 * additional task management methods such as filtering, sorting, or searching.</p>
 * 
 * @author Wang Ziwen & AIs
 * @version 1.0
 */
public class TaskList {
    private final ArrayList<Task> tasks;

    /**
     * Constructs a new TaskList with the specified task collection.
     * 
     * @param tasks the initial collection of tasks
     */
    public TaskList(ArrayList<Task> tasks) {
        this.tasks = tasks;
    }

    /**
     * Returns the underlying task collection as an ArrayList.
     * 
     * <p>This method provides direct access to the task collection for
     * operations that need to modify the list directly.</p>
     * 
     * @return the ArrayList containing all tasks
     */
    public ArrayList<Task> asList() {
        return tasks;
    }
}
