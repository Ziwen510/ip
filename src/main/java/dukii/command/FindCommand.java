package dukii.command;

import dukii.task.TaskList;
import dukii.ui.Ui;
import dukii.storage.Storage;

/**
 * Command implementation for finding tasks by keyword.
 * 
 * <p>The find command allows users to search for tasks that contain a specific
 * keyword in their description. This is useful for quickly locating relevant
 * tasks without having to scroll through the entire task list.</p>
 * 
 * <p>The command format is: "find &lt;keyword&gt;" where keyword is the
 * search term to look for in task descriptions.</p>
 * 
 * @author Wang Ziwen & AIs
 * @version 1.0
 */
public class FindCommand extends Command {
    private final String keyword;
    
    /**
     * Constructs a new FindCommand with the specified search keyword.
     * 
     * @param keyword the keyword to search for in task descriptions
     */
    public FindCommand(String keyword) {
        this.keyword = keyword;
    }
    
    /**
     * Executes the find command by searching for tasks containing the keyword.
     * 
     * <p>This method searches through all tasks in the task list and finds
     * those whose descriptions contain the specified keyword (case-insensitive).
     * It then displays the matching tasks with their sequential numbers.</p>
     * 
     * @param tasks the task list to search through
     * @param ui the user interface for displaying messages
     * @param storage the storage system (not used in this command)
     */
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) {
        if (keyword.isEmpty()) {
            ui.showMessage("Sweetie, please tell me what you're looking for! Use: find <keyword>");
            return;
        }
        
        if (tasks.asList().isEmpty()) {
            ui.showMessage("No tasks to search through! Your list is empty.");
            return;
        }
        
        final String normalizedKeyword = keyword.toLowerCase();
        java.util.List<dukii.task.Task> matchingTasks = new java.util.ArrayList<>();
        java.util.List<dukii.task.Task> allTasks = tasks.asList();
        
        for (int i = 0; i < allTasks.size(); i++) {
            dukii.task.Task task = allTasks.get(i);
            if (task.getDescription().toLowerCase().contains(normalizedKeyword)) {
                matchingTasks.add(task);
            }
        }
        
        if (matchingTasks.isEmpty()) {
            ui.showMessage("Sorry honey, I couldn't find any tasks containing '" + keyword + "'.");
            return;
        }
        
        ui.showMessage("Here are the tasks I found containing '" + keyword + "':");
        for (int i = 0; i < matchingTasks.size(); i++) {
            dukii.task.Task task = matchingTasks.get(i);
            int originalIndex = allTasks.indexOf(task) + 1;
            ui.showMessage(originalIndex + "." + task.toString());
        }
    }
    
    /**
     * Indicates that this command does not cause the application to exit.
     * 
     * @return false since this command doesn't exit the application
     */
    @Override
    public boolean isExit() {
        return false;
    }
    
    /**
     * Indicates that this command does not modify the task storage.
     * 
     * <p>Since finding tasks is a read-only operation, this method returns
     * false to prevent unnecessary save operations.</p>
     * 
     * @return false since this command doesn't modify storage
     */
    @Override
    public boolean modifiesStorage() {
        return false;
    }
}
