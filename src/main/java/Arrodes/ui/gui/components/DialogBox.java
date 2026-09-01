package arrodes.ui.gui.components;

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


public class DialogBox extends HBox {
    private static final Image IMAGE_OF_USER = new Image(DialogBox.class.getResourceAsStream("/images/user.jpg"));
    private static final Image IMAGE_OF_ARRODES = new Image(DialogBox.class.getResourceAsStream("/images/arrodes.jpg"));

    @FXML
    private Label dialog;
    @FXML
    private ImageView displayPicture;

    public DialogBox(String message, Image image) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(DialogBox.class.getResource("/view/DialogBox.fxml"));
            fxmlLoader.setController(this);
            fxmlLoader.setRoot(this);
            fxmlLoader.load();
        } catch (IOException e) {
            System.out.println("Failed to Load Dialog box");
        }
        dialog.setText(message);
        displayPicture.setImage(image);

    }

    /**
     * Flips the dialog box such that the ImageView is on the left and text on the right.
     */
    private void flip() {
        ObservableList<Node> tmp = FXCollections.observableArrayList(this.getChildren());
        Collections.reverse(tmp);
        getChildren().setAll(tmp);
        setAlignment(Pos.TOP_LEFT);
    }

    public static DialogBox getUserDialogBox(String message) {
        return new DialogBox(message, IMAGE_OF_USER);
    }

    public static DialogBox getArrodesDialogBox(String message) {
        DialogBox db = new DialogBox(message, IMAGE_OF_ARRODES);
        db.flip();
        return db;
    }
}
