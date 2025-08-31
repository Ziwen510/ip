package dukii.command;

import dukii.task.TaskList;
import dukii.ui.Ui;
import dukii.storage.Storage;

/**
 * Command implementation for exiting the Dukii application.
 * 
 * <p>The bye command gracefully terminates the application after displaying
 * a farewell message to the user. This command overrides the default behavior
 * of other commands by indicating that it should exit the application and
 * that it doesn't modify the task storage.</p>
 * 
 * <p>The command format is simply "bye" with no additional parameters.</p>
 * 
 * @author Wang Ziwen & AIs
 * @version 1.0
 */
public class ByeCommand extends Command {
    /**
     * Executes the bye command by displaying a farewell message.
     * 
     * <p>This method shows a goodbye message to the user. The actual
     * application termination is handled by the main loop when it detects
     * that this command's isExit() method returns true.</p>
     * 
     * @param tasks the task list (not used in this command)
     * @param ui the user interface for displaying the farewell message
     * @param storage the storage system (not used in this command)
     */
    
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) {
        ui.showMessage("Bye~ Have a good day!");
    }
    
    
    /**
     * Indicates that this command should terminate the application.
     * 
     * <p>When the main loop detects that this method returns true, it will
     * break out of the input loop and close the application.</p>
     * 
     * @return true to indicate that the application should exit
     */
    @Override 
    
    public boolean isExit() {
        
        return true;
    
    }
    
    
    /**
     * Indicates that this command does not modify the task storage.
     * 
     * <p>Since exiting the application doesn't change any task data,
     * this method returns false to prevent unnecessary save operations.</p>
     * 
     * @return false since this command doesn't modify storage
     */
    @Override 
    
    public boolean modifiesStorage() {
        
        return false;
    
    }
}
