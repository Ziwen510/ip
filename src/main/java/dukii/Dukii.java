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

public class Dukii {
    private static final String STORAGE_FILE_PATH = "./data/dukii.txt";
    private static final ArrayList<Task> TASKS = new ArrayList<>();
    private static final TaskList TASK_LIST = new TaskList(TASKS);
    private static final Storage STORAGE = new Storage(STORAGE_FILE_PATH);
    private static final Ui UI = new Ui();
    
    public static void main(String[] args) {
        UI.showWelcome();
        try {
            ArrayList<Task> loadedTasks = STORAGE.load();
            TASKS.clear();
            TASKS.addAll(loadedTasks);
        } catch (IOException e) {
            UI.showMessage("Oh no my sweety, I couldn't load your tasks. Starting fresh.");
        }

        Parser parser = new Parser();
        Scanner scanner = new Scanner(System.in);
        
        while (scanner.hasNextLine()) {
            String input = scanner.nextLine();
            try {
                Command command = parser.parse(input);
                command.execute(TASK_LIST, UI, STORAGE);
                
                if (command.modifiesStorage()) {
                    saveSafely();
                }
                
                if (command.isExit()) {
                    break;
                }
            } catch (DukiiException e) {
                UI.showMessage("Oopsie! " + e.getMessage());
            } catch (Exception e) {
                UI.showMessage("Oh no my sweety, something unexpected happened. Please try again.");
            }
        }
        
        scanner.close();
    }
    
    private static void saveSafely() {
        try {
            STORAGE.save(TASK_LIST.asList());
        } catch (IOException e) {
            UI.showMessage("Oh no my sweety, I couldn't save your tasks. Please try again.");
        }
    }
}


