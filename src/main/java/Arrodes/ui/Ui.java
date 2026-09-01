package arrodes.ui;

import java.util.Scanner;

/** Handles console input and output for Arrodes. */
public class Ui {
    /** Line printed between user-interface messages. */
    private static final String SEPARATOR = "_".repeat(60);

    /** ASCII-art banner displayed when the application starts. */
    private static final String BANNER = """
            _                       _
           / \\   _ __ _ __ ___   __| | ___  ___
          / _ \\ | '__| '__/ _ \\ / _` |/ _ \\/ __|
         / ___ \\| |  | | | (_) | (_| |  __/\\__ \\
        /_/   \\_\\_|  |_|  \\___/ \\__,_|\\___||___/
        """;

    /** Greeting displayed when the application starts. */
    private static final String GREETING_MESSAGE = "Eyes that watch All living Beings\n"
            + "The Stigmata from the Primordial Land\n"
            + "The Great Arrodes is before you!\n"
            + "State your request!";

    /** Farewell displayed when the user exits the application. */
    private static final String BYE_MESSAGE = "I shall await your next request...";

    /** Reads commands from standard input. */
    private final Scanner scanner = new Scanner(System.in);
    /** Creates a console user interface. */
    public Ui() {
    }

    /**
     * Reads one complete command line.
     * @return next line entered by the user
     */
    public String readUserCommand() {
        return scanner.nextLine();
    }

    /**
     * Prints an arbitrary application message.
     * @param message text to print
     */
    public void showMessage(String message) {
        System.out.println(message);
    }

    /** Prints the startup banner and greeting. */
    public void showOnLoadMessage() {
        showSeparator();
        System.out.print(BANNER);
        System.out.println(GREETING_MESSAGE);
    }

    /** Prints the standard separator line. */
    public void showSeparator() {
        System.out.println(SEPARATOR);
    }

    /** Prints the application farewell. */
    public void showOnExitMessage() {
        System.out.println(BYE_MESSAGE);
    }
}
