package dukii.ui;

import java.io.IOException;
import java.util.Collections;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

/**
 * A reusable dialog bubble used in the GUI conversation view.
 *
 * <p>Provides factory methods to create dialog bubbles for the user and for
 * Dukii, rendering avatar image and message text with appropriate alignment.</p>
 */
public final class DialogBox extends HBox {
    @FXML
    private Label dialog;
    @FXML
    private ImageView displayPicture;

    private DialogBox(final String text, final Image img) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(MainWindow.class.getResource("/view/DialogBox.fxml"));
            fxmlLoader.setController(this);
            fxmlLoader.setRoot(this);
            fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        dialog.setText(text);
        displayPicture.setImage(img);
    }

    private void flip() {
        ObservableList<Node> tmp = FXCollections.observableArrayList(this.getChildren());
        Collections.reverse(tmp);
        getChildren().setAll(tmp);
        setAlignment(Pos.TOP_LEFT);
    }

    /**
     * Creates a dialog bubble representing the user's message.
     *
     * @param text the message content
     * @param img the avatar image to display for the user
     * @return a dialog box aligned to the right
     */
    public static DialogBox getUserDialog(final String text, final Image img) {
        return new DialogBox(text, img);
    }

    /**
     * Creates a dialog bubble representing Dukii's reply.
     *
     * @param text the reply content
     * @param img the avatar image to display for Dukii
     * @return a dialog box aligned to the left
     */
    public static DialogBox getDukiiDialog(final String text, final Image img) {
        DialogBox db = new DialogBox(text, img);
        db.flip();
        return db;
    }
}


