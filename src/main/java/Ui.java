import java.util.Base64;

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

    public Ui() {
    }

    public void showOnLoadMessage() {
        System.out.println(BANNER);
        System.out.println(GREETING_MESSAGE);
    }

    public void showSeparator() {
        System.out.println(SEPARATOR);
    }

    public void showOnExitMessage() {
        System.out.println(BYE_MESSAGE);
    }
}
