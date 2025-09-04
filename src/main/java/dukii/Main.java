package dukii;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import dukii.ui.DialogBox;

public class Main extends Application {
    private ScrollPane scrollPane;
    private VBox dialogContainer;
    private TextField userInput;
    private Button sendButton;
    private AnchorPane mainLayout;
    private Image userImage;
    private Image dukiiImage;

    @Override
    public void start(Stage stage) {
        userImage = new Image(this.getClass().getResourceAsStream("/images/user.jpg"));
        dukiiImage = new Image(this.getClass().getResourceAsStream("/images/dukii.jpeg"));
        scrollPane = new ScrollPane();
        dialogContainer = new VBox(8);
        userInput = new TextField();
        sendButton = new Button("Send");
        mainLayout = new AnchorPane();

        scrollPane.setContent(dialogContainer);
        scrollPane.setFitToWidth(true);
        dialogContainer.setPadding(new Insets(10));

        mainLayout.getChildren().addAll(scrollPane, userInput, sendButton);

        AnchorPane.setTopAnchor(scrollPane, 1.0);
        AnchorPane.setLeftAnchor(scrollPane, 1.0);
        AnchorPane.setRightAnchor(scrollPane, 1.0);
        AnchorPane.setBottomAnchor(scrollPane, 45.0);

        AnchorPane.setLeftAnchor(userInput, 1.0);
        AnchorPane.setRightAnchor(userInput, 70.0);
        AnchorPane.setBottomAnchor(userInput, 8.0);

        AnchorPane.setRightAnchor(sendButton, 8.0);
        AnchorPane.setBottomAnchor(sendButton, 8.0);

        Scene scene = new Scene(mainLayout, 400, 500);
        stage.setScene(scene);
        stage.setTitle("Dukii");
        stage.show();

        scrollPane.vvalueProperty().bind(dialogContainer.heightProperty());

        sendButton.setOnAction(event -> handleUserInput());
        userInput.setOnAction(event -> handleUserInput());
    }

    private void handleUserInput() {
        String input = userInput.getText();
        if (input == null || input.trim().isEmpty()) {
            return;
        }
        DialogBox dukiiBox = new DialogBox(input, dukiiImage);
        dukiiBox.alignLeft();
        DialogBox userBox = new DialogBox(input, userImage);
        dialogContainer.getChildren().addAll(userBox, dukiiBox);
        userInput.clear();
    }

    public static void main(String[] args) {
        launch(args);
    }
}


