package arrodes;
import arrodes.command.Command;
import arrodes.exception.ArrodesException;
import arrodes.parser.CommandParser;
import arrodes.storage.Storage;
import arrodes.task.TaskList;
import arrodes.ui.Ui;

/**
 * Runs the Arrodes command-line application.
 */
public class Arrodes {
    /** Maximum number of requests Arrodes can remember during one session. */
    private static final int MAX_ITEMS = 100;

    private Storage storage;
    private TaskList taskList;
    private final Ui ui;

    /** Creates an Arrodes application and loads its saved tasks. */
    public Arrodes() {
        this.storage = new Storage();
        try {
            taskList = storage.load(MAX_ITEMS);
        } catch (ArrodesException exception) {
            System.out.println(exception.getMessage());
            taskList = new TaskList(MAX_ITEMS);
        }
        this.ui = new Ui();
    }
    void run() {
        boolean isExit = false;
        ui.showOnLoadMessage();
        ui.showSeparator();
        while (!isExit) {
            String userCommand = ui.readUserCommand();
            ui.showSeparator();
            try {
                Command command = CommandParser.parse(userCommand);
                command.execute(ui, taskList, storage);
                isExit = command.isExit();
            } catch (ArrodesException knownArrodesException) {
                System.out.println(knownArrodesException.getMessage());
            } finally {
                ui.showSeparator();
            }
            if (isExit) {
                break;
            }
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
