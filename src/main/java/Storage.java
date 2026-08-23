import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.nio.file.AtomicMoveNotSupportedException;
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
        if (dataFile == null || dataFile.getFileName() == null) {
            throw new IllegalArgumentException("Storage file path must name a file.");
        }
        this.dataFile = dataFile;
    }

    /**
     * Saves all tasks in the supplied list, replacing the previous snapshot.
     *
     * @param taskList tasks to save
     * @throws ArrodesException if the data directory or file cannot be written
     */
    public void save(TaskList taskList) throws ArrodesException {
        if (taskList == null) {
            throw new ArrodesException("Arrodes could not save your requests.");
        }
        List<String> lines = new ArrayList<>();
        for (int i = 0; i < taskList.getSize(); i++) {
            try {
                lines.add(formatTask(taskList.getTaskByIndex(i)));
            } catch (RuntimeException exception) {
                throw new ArrodesException("Arrodes could not save your requests.");
            }
        }

        Path temporaryFile = null;
        try {
            Path targetFile = dataFile.toAbsolutePath().normalize();
            Path parent = targetFile.getParent();
            Files.createDirectories(parent);
            temporaryFile = Files.createTempFile(parent, ".arrodes-", ".tmp");
            Files.write(temporaryFile, lines, StandardCharsets.UTF_8);
            try {
                Files.move(temporaryFile, targetFile, StandardCopyOption.ATOMIC_MOVE,
                        StandardCopyOption.REPLACE_EXISTING);
            } catch (AtomicMoveNotSupportedException exception) {
                Files.move(temporaryFile, targetFile, StandardCopyOption.REPLACE_EXISTING);
            }
        } catch (IOException | SecurityException exception) {
            throw new ArrodesException("Arrodes could not save your requests.");
        } finally {
            if (temporaryFile != null) {
                try {
                    Files.deleteIfExists(temporaryFile);
                } catch (IOException ignored) {
                    // The target was already replaced; a leftover temporary file is harmless.
                }
            }
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
        if (capacity < 0) {
            throw new ArrodesException("Arrodes could not load your requests.");
        }
        TaskList taskList = new TaskList(capacity);
        try {
            if (!Files.exists(dataFile)) {
                return taskList;
            }
            if (!Files.isRegularFile(dataFile)) {
                throw new IOException("Storage path is not a regular file.");
            }
            for (String line : Files.readAllLines(dataFile, StandardCharsets.UTF_8)) {
                if (!line.isBlank()) {
                    taskList.insert(parseTask(line));
                }
            }
        } catch (IOException | SecurityException exception) {
            throw new ArrodesException("Arrodes could not load your requests.");
        } catch (ArrodesException exception) {
            throw new ArrodesException("Arrodes could not load your requests.");
        }
        return taskList;
    }

    /** Parses one saved line and restores its completion status. */
    private Task parseTask(String line) throws ArrodesException {
        List<String> fields = splitFields(line);
        if (fields.size() < 3 || fields.get(0).isBlank() || fields.get(2).isBlank()) {
            throw invalidRecord();
        }

        Task task;
        switch (fields.get(0)) {
        case "T":
            if (fields.size() != 3) throw invalidRecord();
            task = new Todo(fields.get(2));
            break;
        case "D":
            if (fields.size() != 4 || fields.get(3).isBlank()) throw invalidRecord();
            task = new Deadline(fields.get(2), fields.get(3));
            break;
        case "E":
            if (fields.size() != 5 || fields.get(3).isBlank() || fields.get(4).isBlank()) throw invalidRecord();
            task = new Event(fields.get(2), fields.get(3), fields.get(4));
            break;
        default:
            throw invalidRecord();
        }

        if ("1".equals(fields.get(1))) {
            task.markAsDone();
        } else if (!"0".equals(fields.get(1))) {
            throw invalidRecord();
        }
        return task;
    }

    /** Splits on unescaped separators and decodes escaped field characters. */
    private List<String> splitFields(String line) throws ArrodesException {
        List<String> fields = new ArrayList<>();
        StringBuilder field = new StringBuilder();
        boolean escaping = false;
        for (int i = 0; i < line.length(); i++) {
            char current = line.charAt(i);
            if (escaping) {
                if (current != '\\' && current != '|') {
                    field.append('\\');
                }
                field.append(current);
                escaping = false;
            } else if (current == '\\') {
                escaping = true;
            } else if (current == '|') {
                fields.add(field.toString().strip());
                field.setLength(0);
            } else {
                field.append(current);
            }
        }
        if (escaping) {
            throw invalidRecord();
        }
        fields.add(field.toString().strip());
        return fields;
    }

    /** Creates the common exception for malformed storage records. */
    private ArrodesException invalidRecord() {
        return new ArrodesException("Arrodes could not load your requests.");
    }

    /** Converts one task into the storage format. */
    private String formatTask(Task task) {
        if (task == null || task.getDescription() == null || task.getDescription().isBlank()
                || containsLineBreak(task.getDescription())) {
            throw new IllegalArgumentException("Task description cannot be null or span multiple lines.");
        }
        String status = task.isDone() ? "1" : "0";
        if (task instanceof Deadline deadline) {
            return String.format("D | %s | %s | %s", status, encode(deadline.getDescription()),
                    encodeRequired(deadline.getDueBy()));
        }
        if (task instanceof Event event) {
            return String.format("E | %s | %s | %s | %s", status, encode(event.getDescription()),
                    encodeRequired(event.getStartAt()), encodeRequired(event.getEndAt()));
        }
        if (!(task instanceof Todo)) {
            throw new IllegalArgumentException("Unknown task type.");
        }
        return String.format("T | %s | %s", status, encode(task.getDescription()));
    }

    /** Escapes characters that have meaning in the storage format. */
    private String encode(String value) {
        if (value == null || containsLineBreak(value)) {
            throw new IllegalArgumentException("Storage fields cannot be null or span multiple lines.");
        }
        return value.replace("\\", "\\\\").replace("|", "\\|");
    }

    /** Encodes a required task-specific field. */
    private String encodeRequired(String value) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("Storage fields cannot be blank.");
        }
        return encode(value);
    }

    /** Returns whether a value would corrupt the line-oriented format. */
    private boolean containsLineBreak(String value) {
        return value.indexOf('\n') >= 0 || value.indexOf('\r') >= 0;
    }
}
