import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/**
 * Saves Arrodes tasks to a plain-text file.
 *
 * <p>Reading is deliberately not included yet. Each line stores the task type,
 * completion status, description, and any type-specific time information.</p>
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
