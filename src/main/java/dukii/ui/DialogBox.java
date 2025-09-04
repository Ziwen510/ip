package dukii.ui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

public class DialogBox extends HBox {
    private final Label text;
    private final ImageView displayPicture;

    public DialogBox(final String message, final Image avatar) {
        this.text = new Label(message);
        this.displayPicture = new ImageView(avatar);

        this.text.setWrapText(true);
        this.displayPicture.setFitWidth(100.0);
        this.displayPicture.setFitHeight(100.0);
        this.setAlignment(Pos.TOP_RIGHT);

        this.getChildren().addAll(this.text, this.displayPicture);
    }

    private void flip() {
        this.setAlignment(Pos.TOP_LEFT);
        ObservableList<Node> tmp = FXCollections.observableArrayList(this.getChildren());
        FXCollections.reverse(tmp);
        this.getChildren().setAll(tmp);
    }

    public static DialogBox getUserDialog(final String message, final Image avatar) {
        return new DialogBox(message, avatar);
    }

    public static DialogBox getDukeDialog(final String message, final Image avatar) {
        DialogBox db = new DialogBox(message, avatar);
        db.flip();
        return db;
    }
}


