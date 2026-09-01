package arrodes;

import arrodes.command.Command;
import arrodes.exception.ArrodesException;
import arrodes.parser.CommandParser;
import arrodes.storage.Storage;
import arrodes.task.TaskList;
import arrodes.ui.cli.Cli;
import arrodes.ui.gui.Gui;
import arrodes.ui.gui.GuiListener;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.stage.Stage;


/**
 * Runs the Arrodes command-line application.
 */
public class Arrodes extends Application {
    /** Maximum number of requests Arrodes can remember during one session. */
    private static final int MAX_ITEMS = 100;
    private Storage storage;
    private TaskList taskList;

    private Gui gui;
    private Cli cli;

    /** Creates an Arrodes application and loads its saved tasks. */
    public Arrodes() {
        this.storage = new Storage();
        try {
            taskList = storage.load(MAX_ITEMS);
        } catch (ArrodesException exception) {
            System.out.println(exception.getMessage());
            taskList = new TaskList(MAX_ITEMS);
        }
    }

    /**
     * Loads the graphical user interface layout and displays it in a window.
     *
     * @param stage primary application window
     */
    @Override
    public void start(Stage stage) {
        gui = new Gui();
        gui.attachInputListener((GuiListener) this::inputListener);

        Scene scene = new Scene(gui);
        stage.setTitle("Arrodes");
        stage.setScene(scene);
        stage.setMinHeight(220);
        stage.setMinWidth(417);
        stage.show();
    }

    void run() {
        cli = new Cli();
        boolean isExit = false;
        cli.showOnLoadMessage();
        cli.showSeparator();
        while (!isExit) {
            String userCommand = cli.readUserInput();
            cli.showSeparator();
            try {
                Command command = CommandParser.parse(userCommand);
                command.execute(cli, taskList, storage);
                isExit = command.isExit();
            } catch (ArrodesException knownArrodesException) {
                System.out.println(knownArrodesException.getMessage());
            } finally {
                cli.showSeparator();
            }
            if (isExit) {
                break;
            }
        }
    }

    private void inputListener(String userInput) {
        try {
            Command command = CommandParser.parse(userInput);
            command.execute(gui, taskList, storage);
            if (command.isExit()) {
                gui.showOnExitMessage();
                Platform.exit();
            }
        } catch (ArrodesException knownArrodesException) {
            gui.showMessage(knownArrodesException.getMessage());
        }
    }

    /**
     * Greets the user, stores each ordinary request as a task, lists stored tasks,
     * marks or unmarks numbered tasks, and exits when the user enters {@code bye}.
     *
     * @param args command-line arguments, which are not used
     */
    public static void main(String[] args) {
        Arrodes chatbot = new Arrodes();
        chatbot.run();
    }

}
