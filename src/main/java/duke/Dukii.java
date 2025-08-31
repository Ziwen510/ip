package duke;

import java.util.Scanner;
import java.util.ArrayList;
import java.io.IOException;
import duke.ui.Ui;
import duke.task.TaskList;
import duke.task.Task;
import duke.storage.Storage;
import duke.parser.Parser;
import duke.command.Command;
import duke.exception.DukiiException;

public class Dukii {
    private static ArrayList<Task> tasks = new ArrayList<>();
    private static TaskList taskList = new TaskList(tasks);
    private static Storage storage = new Storage("./data/dukii.txt");
    private static Ui ui = new Ui();
    
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
    
    private static void saveSafely() {
        try {
            storage.save(taskList.asList());
        } catch (IOException e) {
            ui.showMessage("Oh no my sweety, I couldn't save your tasks. Please try again.");
        }
    }
}


