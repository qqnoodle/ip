import java.util.Map;
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

        boolean EXIT_FLAG = false;
        Scanner scanner = new Scanner(System.in);
        TaskList taskList = new TaskList(MAX_ITEMS);

        while (scanner.hasNextLine()) {
            String command = scanner.nextLine();
            System.out.println(SEPARATOR);
            try {
                ParsedCommand parsedCommand = CommandParser.parse(command);
                Command commandKeyword = parsedCommand.getCommand();
                String description = parsedCommand.getDescription();
                Map<String ,String> parameters = parsedCommand.getParameters();
                switch (commandKeyword) {
                    case BYE:
                        System.out.println(BYE_MESSAGE);
                        EXIT_FLAG = true;
                        break;
                    case LIST:
                        if (!description.isBlank()) throw new ArrodesException(ArrodesException.UNKNOWN_COMMAND);
                        System.out.println("Arrodes recalls your requests:");
                        for (int i = 0; i < taskList.getSize(); i++) {
                            System.out.println((i + 1) + "." + taskList.getTaskByIndex(i).toString());
                        }
                        break;
                    case MARK:
                        int markTaskNumber = Integer.parseInt(description);
                        Task unmarkedTask = taskList.getTaskByNumber(markTaskNumber);
                        unmarkedTask.markAsDone();
                        System.out.println("A worthy task! Arrodes has marked it as done:");
                        System.out.println("  " + unmarkedTask);
                        break;
                    case UNMARK:
                        int unmarkTaskNumber = Integer.parseInt(description);
                        Task markedTask = taskList.getTaskByNumber(unmarkTaskNumber);
                        markedTask.markAsNotDone();
                        System.out.println("As you decree, Arrodes has marked this task as not done yet:");
                        System.out.println("  " + markedTask);
                        break;
                    case TODO:
                        if (!parameters.isEmpty()) throw new ArrodesException(ArrodesException.INCORRECT_PARAMS);
                        taskList.insert(new Todo(description));
                        System.out.println("Inscribing request: \n"
                                + "   " + taskList.getTaskByNumber(taskList.getSize()).toString() + "\n"
                                + taskList.getSize() + " tasks are being tracked");
                        break;
                    case DEADLINE:
                        // Deadline requires /by
                        if (parameters.size() != 1) throw new ArrodesException(ArrodesException.INCORRECT_PARAMS_COUNT);
                        if (!parameters.containsKey("by")) throw new ArrodesException(ArrodesException.INCORRECT_PARAMS);
                        taskList.insert(new Deadline(description, parameters.get("by")));
                        System.out.println("Inscribing request: \n"
                                + "   " + taskList.getTaskByNumber(taskList.getSize()).toString() + "\n"
                                + taskList.getSize() + " tasks are being tracked");
                        break;
                    case EVENT:
                        // Event requires /from /to
                        if (parameters.size() != 2) throw new ArrodesException(ArrodesException.INCORRECT_PARAMS_COUNT);
                        if (!parameters.containsKey("from")) throw new ArrodesException(ArrodesException.INCORRECT_PARAMS);
                        if (!parameters.containsKey("to")) throw new ArrodesException(ArrodesException.INCORRECT_PARAMS);
                        taskList.insert(new Event(description, parameters.get("from"), parameters.get("to")));
                        System.out.println("Inscribing request: \n"
                                + "   " + taskList.getTaskByNumber(taskList.getSize()).toString() + "\n"
                                + taskList.getSize() + " tasks are being tracked");
                        break;
                    default:
                        throw new ArrodesException(ArrodesException.UNKNOWN_COMMAND);
                }
            } catch (ArrodesException knownArrodesException) {
                System.out.println(knownArrodesException.getMessage());
            } catch (NumberFormatException numberFormatException) {
                System.out.println("Name the task number for Arrodes to handle, such as: mark/unmark 2");
            } finally {
                System.out.println(SEPARATOR);
            }
            if (EXIT_FLAG) break;
        }
    }
}
