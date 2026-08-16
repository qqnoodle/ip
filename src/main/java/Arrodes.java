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
     * marks or unmarks numbered tasks, and exits when the user enters {@code bye}.
     *
     * @param args command-line arguments, which are not used
     */
    public static void main(String[] args) {
        System.out.println(SEPARATOR);
        System.out.print(BANNER);
        System.out.println(GREETING_MESSAGE);
        System.out.println(SEPARATOR);

        Scanner scanner = new Scanner(System.in);
        Task[] tasks = new Task[MAX_ITEMS];
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
                    System.out.println((i + 1) + ".[" + tasks[i].getStatusIcon() + "] "
                            + tasks[i].getDescription());
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
                        tasks[taskIndex].markAsDone();
                        System.out.println("A worthy task! Arrodes has marked it as done:");
                        System.out.println("  " + tasks[taskIndex].toString());
                    }
                } catch (NumberFormatException exception) {
                    System.out.println("Name the task number for Arrodes to mark, such as: mark 2");
                }
                System.out.println(SEPARATOR);
            } else if (command.startsWith("unmark ")) {
                String taskNumberText = command.substring(7).trim();
                try {
                    int taskNumber = Integer.parseInt(taskNumberText);
                    if (taskNumber < 1 || taskNumber > itemCount) {
                        System.out.println("That task does not appear in Arrodes' annals.");
                    } else {
                        int taskIndex = taskNumber - 1;
                        tasks[taskIndex].markAsNotDone();
                        System.out.println("As you decree, Arrodes has marked this task as not done yet:");
                        System.out.println("  " + tasks[taskIndex].toString());
                    }
                } catch (NumberFormatException exception) {
                    System.out.println("Name the task number for Arrodes to unmark, such as: unmark 2");
                }
                System.out.println(SEPARATOR);
            } else if (itemCount < MAX_ITEMS) {
                tasks[itemCount] = new Task(command);
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
