import java.util.Scanner;

/**
 * Runs the Arrodes command-line application.
 */
public class Arrodes {
    /** Maximum number of requests Arrodes can remember during one session. */
    private static final int MAX_ITEMS = 100;

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

    /**
     * Greets the user, stores each ordinary request as a task, lists stored tasks,
     * marks numbered tasks as done, and exits when the user enters {@code bye}.
     *
     * @param args command-line arguments, which are not used
     */
    public static void main(String[] args) {
        System.out.println(SEPARATOR);
        System.out.print(BANNER);
        System.out.println(GREETING_MESSAGE);
        System.out.println(SEPARATOR);

        Scanner scanner = new Scanner(System.in);
        String[] items = new String[MAX_ITEMS];
        boolean[] isDone = new boolean[MAX_ITEMS];
        int itemCount = 0;

        while (scanner.hasNextLine()) {
            String command = scanner.nextLine();
            System.out.println(SEPARATOR);

            if (command.equals("bye")) {
                System.out.println(BYE_MESSAGE);
                System.out.println(SEPARATOR);
                break;
            } else if (command.equals("list")) {
                System.out.println("Arrodes recalls your requests:");
                for (int i = 0; i < itemCount; i++) {
                    String status = isDone[i] ? "[X]" : "[ ]";
                    System.out.println((i + 1) + "." + status + " " + items[i]);
                }
                System.out.println(SEPARATOR);
            } else if (command.startsWith("mark ")) {
                String taskNumberText = command.substring(5).trim();
                try {
                    int taskNumber = Integer.parseInt(taskNumberText);
                    if (taskNumber < 1 || taskNumber > itemCount) {
                        System.out.println("That task does not appear in Arrodes' annals.");
                    } else {
                        int taskIndex = taskNumber - 1;
                        isDone[taskIndex] = true;
                        System.out.println("A worthy task! Arrodes has marked it as done:");
                        System.out.println("  [X] " + items[taskIndex]);
                    }
                } catch (NumberFormatException exception) {
                    System.out.println("Name the task number for Arrodes to mark, such as: mark 2");
                }
                System.out.println(SEPARATOR);
            } else if (itemCount < MAX_ITEMS) {
                items[itemCount] = command;
                itemCount++;
                System.out.println("The request has been inscribed: " + command);
                System.out.println(SEPARATOR);
            } else {
                System.out.println("The annals of Arrodes are full; no further request can be inscribed.");
                System.out.println(SEPARATOR);
            }
        }
    }
}
