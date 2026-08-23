import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/**
 * Saves Arrodes tasks to a plain-text file.
 *
 * <p>Each line stores the task type, completion status, description, and any
 * type-specific time information.</p>
 */
public class Storage {
    /** Location of the task data relative to the project root. */
    private static final Path DEFAULT_DATA_FILE = Path.of("data", "arrodes.txt");

    /** File used by this storage instance. */
    private final Path dataFile;

    /** Creates storage using the application's default data file. */
    public Storage() {
        this(DEFAULT_DATA_FILE);
    }

    /**
     * Creates storage using a specific file.
     *
     * <p>The path overload keeps file-writing tests isolated from the
     * application's real data file.</p>
     *
     * @param dataFile file to write
     */
    public Storage(Path dataFile) {
        this.dataFile = dataFile;
    }

    /**
     * Saves all tasks in the supplied list, replacing the previous snapshot.
     *
     * @param taskList tasks to save
     * @throws ArrodesException if the data directory or file cannot be written
     */
    public void save(TaskList taskList) throws ArrodesException {
        List<String> lines = new ArrayList<>();
        for (int i = 0; i < taskList.getSize(); i++) {
            lines.add(formatTask(taskList.getTaskByIndex(i)));
        }

        try {
            Path parent = dataFile.getParent();
            if (parent != null) {
                Files.createDirectories(parent);
            }
            Files.write(dataFile, lines);
        } catch (IOException exception) {
            throw new ArrodesException("Arrodes could not save your requests.");
        }
    }

    /**
     * Loads the saved tasks into a new task list.
     *
     * @param capacity maximum number of tasks the returned list can contain
     * @return saved tasks, or an empty list when no data file exists
     * @throws ArrodesException if the file cannot be read or contains an invalid record
     */
    public TaskList load(int capacity) throws ArrodesException {
        TaskList taskList = new TaskList(capacity);
        if (!Files.exists(dataFile)) {
            return taskList;
        }

        try {
            for (String line : Files.readAllLines(dataFile)) {
                if (!line.isBlank()) {
                    taskList.insert(parseTask(line));
                }
            }
        } catch (IOException exception) {
            throw new ArrodesException("Arrodes could not load your requests.");
        }
        return taskList;
    }

    /** Parses one saved line and restores its completion status. */
    private Task parseTask(String line) throws ArrodesException {
        String[] fields = line.split("\\s*\\|\\s*", -1);
        if (fields.length < 3 || fields[0].isBlank() || fields[2].isBlank()) {
            throw invalidRecord();
        }

        Task task;
        switch (fields[0]) {
        case "T":
            if (fields.length != 3) throw invalidRecord();
            task = new Todo(fields[2]);
            break;
        case "D":
            if (fields.length != 4 || fields[3].isBlank()) throw invalidRecord();
            task = new Deadline(fields[2], fields[3]);
            break;
        case "E":
            if (fields.length != 5 || fields[3].isBlank() || fields[4].isBlank()) throw invalidRecord();
            task = new Event(fields[2], fields[3], fields[4]);
            break;
        default:
            throw invalidRecord();
        }

        if ("1".equals(fields[1])) {
            task.markAsDone();
        } else if (!"0".equals(fields[1])) {
            throw invalidRecord();
        }
        return task;
    }

    /** Creates the common exception for malformed storage records. */
    private ArrodesException invalidRecord() {
        return new ArrodesException("Arrodes could not load your requests.");
    }

    /** Converts one task into the storage format. */
    private String formatTask(Task task) {
        String status = task.isDone() ? "1" : "0";
        if (task instanceof Deadline deadline) {
            return String.format("D | %s | %s | %s", status, task.getDescription(), deadline.getDueBy());
        }
        if (task instanceof Event event) {
            return String.format("E | %s | %s | %s | %s", status, task.getDescription(),
                    event.getStartAt(), event.getEndAt());
        }
        return String.format("T | %s | %s", status, task.getDescription());
    }
}
