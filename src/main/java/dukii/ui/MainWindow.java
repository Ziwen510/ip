package dukii.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import dukii.command.Command;
import dukii.exception.DukiiException;
import dukii.parser.Parser;
import dukii.storage.Storage;
import dukii.task.Task;
import dukii.task.TaskList;

/**
 * Controller for the main GUI.
 */
public class MainWindow extends AnchorPane {
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private VBox dialogContainer;
    @FXML
    private TextField userInput;
    @FXML
    private Button sendButton;

    private final String STORAGE_FILE_PATH = "./data/dukii.txt";
    private final TaskList tasks = new TaskList(new java.util.ArrayList<Task>());
    private final Storage storage = new Storage(STORAGE_FILE_PATH);
    private final Ui ui = new Ui();
    private final Parser parser = new Parser();

    private final Image userImage = new Image(this.getClass().getResourceAsStream("/images/user.jpg"));
    private final Image dukiiImage = new Image(this.getClass().getResourceAsStream("/images/dukii.jpeg"));

    @FXML
    public void initialize() {
        scrollPane.vvalueProperty().bind(dialogContainer.heightProperty());
        ui.showWelcome();
        String welcome = ui.drainMessages();
        if (!welcome.isEmpty()) {
            dialogContainer.getChildren().add(
                    DialogBox.getDukiiDialog(welcome, dukiiImage)
            );
        }
    }

    // No external injection required; GUI is wired directly to Dukii components.

    @FXML
    private void handleUserInput() {
        String input = userInput.getText();
        String response;
        try {
            if (input == null || input.trim().isEmpty()) {
                return;
            }
            Command command = parser.parse(input);
            command.execute(tasks, ui, storage);
            if (command.modifiesStorage()) {
                storage.save(tasks.asList());
            }
            if (command.isExit()) {
                response = ui.drainMessages();
                dialogContainer.getChildren().addAll(
                        DialogBox.getUserDialog(input, userImage),
                        DialogBox.getDukiiDialog(response, dukiiImage)
                );
                userInput.clear();
                // optional: close window
                this.getScene().getWindow().hide();
                return;
            }
            response = ui.drainMessages();
        } catch (DukiiException | java.io.IOException e) {
            ui.showMessage("Oopsie! " + e.getMessage());
            response = ui.drainMessages();
        } catch (Exception e) {
            ui.showMessage("Oh no my sweety, something unexpected happened. Please try again.");
            response = ui.drainMessages();
        }
        dialogContainer.getChildren().addAll(
                DialogBox.getUserDialog(input, userImage),
                DialogBox.getDukiiDialog(response, dukiiImage)
        );
        userInput.clear();
    }
}


