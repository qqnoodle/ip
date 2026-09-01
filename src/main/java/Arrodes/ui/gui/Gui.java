package arrodes.ui.gui;

import java.io.IOException;

import arrodes.ui.Ui;
import arrodes.ui.gui.components.DialogBox;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;


/**
 * JavaFX controller for the main Arrodes graphical user interface.
 *
 * <p>The layout and controls are defined in {@code Gui.fxml}. JavaFX creates
 * this controller when the FXML file is loaded and injects the controls into
 * the fields below.</p>
 */
public class Gui extends AnchorPane implements Ui {
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private VBox dialogContainer;
    @FXML
    private TextField textInput;
    @FXML
    private Button sendButton;

    private GuiListener inputListener;

    /**
     * Creates the GUI and loads its controls from {@code Gui.fxml}.
     *
     * <p>The FXML loader uses this object as both the root and the controller,
     * so callers can use {@code new Gui()} directly as the scene root.</p>
     */
    public Gui() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Gui.fxml"));
        loader.setRoot(this);
        loader.setController(this);

        try {
            loader.load();
        } catch (IOException exception) {
            throw new IllegalStateException("Unable to load the Arrodes GUI layout", exception);
        }
        showOnLoadMessage();
    }
    public void attachInputListener(GuiListener inputListener) {
        this.inputListener = inputListener;
    }

    /** Performs setup after all {@code @FXML} fields have been injected. */
    @FXML
    public void initialize() {
        sendButton.setOnMouseClicked((event) -> {
            handleUserInput();
        });
        textInput.setOnAction((event) -> {
            handleUserInput();
        });
    }

    private void handleUserInput() {
        String userInput = readUserInput();
        dialogContainer.getChildren().addAll(DialogBox.getUserDialogBox(userInput));
        inputListener.execute(userInput);
    }

    @Override
    public String readUserInput() {
        String userInput = textInput.getText();
        textInput.clear();
        return userInput;
    };
    /**
     * Displays an arbitrary application message.
     * @param message text to print
     */
    @Override
    public void showMessage(String message) {
        dialogContainer.getChildren().addAll(DialogBox.getArrodesDialogBox(message));
    };
    /** Displays the startup banner and greeting. */
    @Override
    public void showOnLoadMessage() {
        showMessage("Eyes that watch All living Beings");
        showMessage("The Stigmata from the Primordial Land");
        showMessage("The Great Arrodes is before you!");
        showMessage("State your request!");

    };

    /** Displays the standard separator line. */
    @Override
    public void showSeparator() {
        showMessage("....");
    };

    /** Displays the application farewell. */
    @Override
    public void showOnExitMessage() {
        showMessage("I shall await your next request...");
    };
}
