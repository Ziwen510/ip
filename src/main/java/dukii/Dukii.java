package dukii;

import java.util.Scanner;
import java.util.ArrayList;
import java.io.IOException;
import dukii.ui.Ui;
import dukii.task.TaskList;
import dukii.task.Task;
import dukii.storage.Storage;
import dukii.parser.Parser;
import dukii.command.Command;
import dukii.exception.DukiiException;

/**
 * Dukii is a personal task management application that allows users to manage
 * their tasks including todos, deadlines, and events. It provides a command-line
 * interface for task operations and persistent storage of tasks.
 * 
 * <p>The application supports the following task types:</p>
 * <ul>
 *   <li>Todo: Simple tasks without time constraints</li>
 *   <li>Deadline: Tasks with a specific due date</li>
 *   <li>Event: Tasks with start and end dates</li>
 * </ul>
 * 
 * <p>Tasks can be marked as done/pending, listed, and deleted. The application
 * automatically saves tasks to persistent storage and loads them on startup.</p>
 * 
 * @author Wang Ziwen & AIs
 * @version 1.0
 */
public class Dukii {
    private static ArrayList<Task> tasks = new ArrayList<>();
    private static TaskList taskList = new TaskList(tasks);
    private static Storage storage = new Storage("./data/dukii.txt");
    private static Ui ui = new Ui();
    
    /**
     * The main entry point of the Dukii application.
     * 
     * <p>This method initializes the application, loads existing tasks from storage,
     * and starts the main command loop. It handles user input, parses commands,
     * executes them, and manages the application lifecycle.</p>
     * 
     * @param args command line arguments (not used)
     */
    public static void main(String[] args) {
        ui.showWelcome();
        try {
            tasks = storage.load();
            taskList = new TaskList(tasks);
        } catch (IOException e) {
            ui.showMessage("Oh no my sweety, I couldn't load your tasks. Starting fresh.");
        }

        Parser parser = new Parser();
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNextLine()) {
            String input = scanner.nextLine();
            try {
                Command command = parser.parse(input);
                command.execute(taskList, ui, storage);
                if (command.modifiesStorage()) {
                    saveSafely();
                }
                if (command.isExit()) {
                    break;
                }
            } catch (DukiiException e) {
                ui.showMessage("Oopsie! " + e.getMessage());
            } catch (Exception e) {
                ui.showMessage("Oh no my sweety, something unexpected happened. Please try again.");
            }
        }
        scanner.close();
    }
    
    /**
     * Safely saves the current task list to persistent storage.
     * 
     * <p>This method handles the saving operation and provides user feedback
     * if the save operation fails. It catches IOException and displays an
     * appropriate error message to the user.</p>
     */
    private static void saveSafely() {
        try {
            storage.save(taskList.asList());
        } catch (IOException e) {
            ui.showMessage("Oh no my sweety, I couldn't save your tasks. Please try again.");
        }
    }
}


