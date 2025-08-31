package dukii.command;

import dukii.task.TaskList;
import dukii.ui.Ui;
import dukii.storage.Storage;
import dukii.exception.DukiiException;

/**
 * Abstract base class for all command implementations in the Dukii application.
 * 
 * <p>This class defines the contract that all command implementations must follow.
 * Commands are responsible for executing specific operations on tasks, updating
 * the user interface, and potentially modifying persistent storage.</p>
 * 
 * <p>Each command implementation should override the {@code execute} method to
 * provide specific functionality. The default implementations of {@code isExit}
 * and {@code modifiesStorage} can be overridden as needed.</p>
 * 
 * @author Wang Ziwen & AIs
 * @version 1.0
 */
public abstract class Command {
    /**
     * Executes the command with the given task list, user interface, and storage.
     * 
     * <p>This method should be implemented by all concrete command classes to
     * perform their specific operations. The method may throw DukiiException
     * if the command execution fails due to invalid input or other errors.</p>
     * 
     * @param tasks the task list to operate on
     * @param ui the user interface for displaying messages
     * @param storage the storage system for persisting changes
     * @throws DukiiException if the command execution fails
     */
    public abstract void execute(TaskList tasks, Ui ui, Storage storage) throws DukiiException;
    
    /**
     * Indicates whether this command should terminate the application.
     * 
     * <p>By default, commands do not terminate the application. Override this
     * method in commands that should cause the application to exit (e.g., bye command).</p>
     * 
     * @return true if the command should exit the application, false otherwise
     */
    public boolean isExit() {
        return false;
    }
    
    /**
     * Indicates whether this command modifies the task storage.
     * 
     * <p>By default, commands are assumed to modify storage. Override this method
     * in commands that don't modify storage (e.g., list command) to prevent
     * unnecessary save operations.</p>
     * 
     * @return true if the command modifies storage, false otherwise
     */
    public boolean modifiesStorage() {
        return true;
    }
}
