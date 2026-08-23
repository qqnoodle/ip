import java.util.Map;
import java.util.Scanner;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

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
        Storage storage = new Storage();
        TaskList taskList;
        try {
            taskList = storage.load(MAX_ITEMS);
        } catch (ArrodesException exception) {
            System.out.println(exception.getMessage());
            taskList = new TaskList(MAX_ITEMS);
        }

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
                        storage.save(taskList);
                        System.out.println("A worthy task! Arrodes has marked it as done:");
                        System.out.println("  " + unmarkedTask);
                        break;
                    case UNMARK:
                        int unmarkTaskNumber = Integer.parseInt(description);
                        Task markedTask = taskList.getTaskByNumber(unmarkTaskNumber);
                        markedTask.markAsNotDone();
                        storage.save(taskList);
                        System.out.println("As you decree, Arrodes has marked this task as not done yet:");
                        System.out.println("  " + markedTask);
                        break;
                    case DELETE:
                        int deleteTaskNumber = Integer.parseInt(description);
                        Task taskToBeDeleted = taskList.getTaskByNumber(deleteTaskNumber);
                        taskList.delete(deleteTaskNumber);
                        storage.save(taskList);
                        System.out.println("Erasing records of the task:\n");
                        System.out.println(taskToBeDeleted + "\n");
                        System.out.println(taskList.getSize() + " tasks remaining are being tracked");
                        break;
                    case TODO:
                        if (!parameters.isEmpty()) throw new ArrodesException(ArrodesException.INCORRECT_PARAMS);
                        requireDescription(description);
                        taskList.insert(new Todo(description));
                        storage.save(taskList);
                        System.out.println("Inscribing request: \n"
                                + "   " + taskList.getTaskByNumber(taskList.getSize()).toString() + "\n"
                                + taskList.getSize() + " tasks are being tracked");
                        break;
                    case DEADLINE:
                        // Deadline requires /by
                        if (parameters.size() != 1) throw new ArrodesException(ArrodesException.INCORRECT_PARAMS_COUNT);
                        if (!parameters.containsKey("by")) throw new ArrodesException(ArrodesException.INCORRECT_PARAMS);
                        requireDescription(description);
                        taskList.insert(new Deadline(description, parseDateTime(parameters.get("by"),
                                "Use a date or date-time in yyyy-MM-dd or yyyy-MM-ddTHH:mm format.")));
                        storage.save(taskList);
                        System.out.println("Inscribing request: \n"
                                + "   " + taskList.getTaskByNumber(taskList.getSize()).toString() + "\n"
                                + taskList.getSize() + " tasks are being tracked");
                        break;
                    case EVENT:
                        // Event requires /from /to
                        if (parameters.size() != 2) throw new ArrodesException(ArrodesException.INCORRECT_PARAMS_COUNT);
                        if (!parameters.containsKey("from")) throw new ArrodesException(ArrodesException.INCORRECT_PARAMS);
                        if (!parameters.containsKey("to")) throw new ArrodesException(ArrodesException.INCORRECT_PARAMS);
                        requireDescription(description);
                        try {
                            String startValue = parameters.get("from");
                            String endValue = parameters.get("to");
                            taskList.insert(new Event(description, parseDateTime(startValue,
                                            "Use event times in yyyy-MM-dd or yyyy-MM-ddTHH:mm format."),
                                    parseDateTime(endValue,
                                            "Use event times in yyyy-MM-dd or yyyy-MM-ddTHH:mm format."),
                                    startValue.contains("T"), endValue.contains("T")));
                        } catch (IllegalArgumentException exception) {
                            throw new ArrodesException(ArrodesException.INVALID_EVENT_TIME);
                        }
                        storage.save(taskList);
                        System.out.println("Inscribing request: \n"
                                + "   " + taskList.getTaskByNumber(taskList.getSize()).toString() + "\n"
                                + taskList.getSize() + " tasks are being tracked");
                        break;
                    case UPCOMING:
                        if (!description.isBlank()) throw new ArrodesException(ArrodesException.UNKNOWN_COMMAND);
                        if (parameters.size() != 1 || !parameters.containsKey("on")) {
                            throw new ArrodesException(ArrodesException.INCORRECT_PARAMS);
                        }
                        printUpcoming(taskList, parameters.get("on"));
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

    /** Rejects task commands that do not contain a meaningful description. */
    private static void requireDescription(String description) throws ArrodesException {
        if (description == null || description.isBlank()) {
            throw new ArrodesException(ArrodesException.EMPTY_DESCRIPTION);
        }
    }

    /** Parses an ISO date or date-time, normalising date-only values to midnight. */
    private static LocalDateTime parseDateTime(String value, String errorMessage) throws ArrodesException {
        boolean hasExpectedShape = value != null
                && value.matches("\\d{4}-\\d{2}-\\d{2}(T\\d{2}:\\d{2})?");
        try {
            return LocalDateTime.parse(value);
        } catch (DateTimeParseException exception) {
            try {
                return LocalDate.parse(value).atStartOfDay();
            } catch (DateTimeParseException ignored) {
                if (hasExpectedShape) {
                    throw new ArrodesException(
                            "That date is invalid because the specified day or time does not exist.");
                }
                throw new ArrodesException(errorMessage);
            }
        }
    }

    /** Prints deadlines due by and events occurring on the requested date or time. */
    private static void printUpcoming(TaskList taskList, String requestedTime) throws ArrodesException {
        LocalDateTime target = parseDateTime(requestedTime,
                "Use a date or date-time in yyyy-MM-dd or yyyy-MM-ddTHH:mm format.");
        boolean dateOnly = !requestedTime.contains("T");
        LocalDate targetDate = target.toLocalDate();
        LocalDateTime dayEnd = targetDate.atTime(LocalTime.MAX);
        DateTimeFormatter displayFormat = DateTimeFormatter.ofPattern(
                dateOnly ? "MMM dd yyyy" : "MMM dd yyyy HH:mm", Locale.ENGLISH);
        LocalDateTime deadlineCutoff = dateOnly ? dayEnd : target;
        boolean found = false;

        System.out.println("Arrodes recalls requests for " + target.format(displayFormat) + ":");
        for (int i = 0; i < taskList.getSize(); i++) {
            Task task = taskList.getTaskByIndex(i);
            boolean matches = false;
            if (task instanceof Deadline deadline) {
                matches = !deadline.getDueBy().isAfter(deadlineCutoff);
            } else if (task instanceof Event event) {
                matches = dateOnly
                        ? !event.getStartAt().toLocalDate().isAfter(targetDate)
                                && !event.getEndAt().toLocalDate().isBefore(targetDate)
                        : !event.getStartAt().isAfter(target) && !event.getEndAt().isBefore(target);
            }
            if (matches) {
                System.out.println((i + 1) + "." + task);
                found = true;
            }
        }
        if (!found) {
            System.out.println("Arrodes found no deadlines or events for that date or time.");
        }
    }
}
