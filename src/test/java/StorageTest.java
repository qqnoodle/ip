import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Comparator;
import java.util.List;

/**
 * Verifies the plain-text snapshots produced by {@link Storage}.
 *
 * <p>This is a dependency-free test executable because the project does not
 * currently define a unit-test framework.</p>
 */
public class StorageTest {
    /** Runs all storage tests. */
    public static void main(String[] args) throws IOException {
        Path temporaryDirectory = Files.createTempDirectory("arrodes-storage-test");
        try {
            testSavesAllTaskTypesAndStatuses(temporaryDirectory.resolve("arrodes.txt"));
            testSaveReplacesPreviousSnapshot(temporaryDirectory.resolve("arrodes.txt"));
            testLoadsAllTaskTypesAndStatuses(temporaryDirectory.resolve("arrodes.txt"));
            testMissingFileLoadsEmptyList(temporaryDirectory.resolve("missing.txt"));
            testMalformedRecordIsRejected(temporaryDirectory.resolve("invalid.txt"));
            System.out.println("All storage tests passed.");
        } finally {
            try (var paths = Files.walk(temporaryDirectory)) {
                paths.sorted(Comparator.reverseOrder()).forEach(path -> {
                    try {
                        Files.deleteIfExists(path);
                    } catch (IOException exception) {
                        throw new RuntimeException(exception);
                    }
                });
            }
        }
    }

    /** Verifies todo, deadline, event, and completed-task serialization. */
    private static void testSavesAllTaskTypesAndStatuses(Path dataFile) throws IOException {
        TaskList taskList = new TaskList();
        taskList.insert(new Todo("read book"));

        Deadline deadline = new Deadline("return book", "June 6th");
        deadline.markAsDone();
        taskList.insert(deadline);
        taskList.insert(new Event("project meeting", "Aug 6th 2pm", "Aug 6th 4pm"));

        new Storage(dataFile).save(taskList);

        List<String> actual = Files.readAllLines(dataFile);
        List<String> expected = List.of(
                "T | 0 | read book",
                "D | 1 | return book | June 6th",
                "E | 0 | project meeting | Aug 6th 2pm | Aug 6th 4pm");
        check(actual.equals(expected), "storage should preserve task types, fields, and statuses");
    }

    /** Verifies that saving an empty list removes tasks from the old snapshot. */
    private static void testSaveReplacesPreviousSnapshot(Path dataFile) throws IOException {
        TaskList taskList = new TaskList();
        taskList.insert(new Todo("temporary task"));
        Storage storage = new Storage(dataFile);
        storage.save(taskList);

        taskList.delete(1);
        storage.save(taskList);

        check(Files.readAllLines(dataFile).isEmpty(), "saving should replace the previous snapshot");
    }

    /** Verifies that saved records are reconstructed as the correct task types. */
    private static void testLoadsAllTaskTypesAndStatuses(Path dataFile) throws IOException {
        Files.write(dataFile, List.of(
                "T | 0 | read book",
                "D | 1 | return book | June 6th",
                "E | 0 | project meeting | Aug 6th 2pm | Aug 6th 4pm"));

        TaskList taskList = new Storage(dataFile).load(100);
        check(taskList.getSize() == 3, "load should restore every saved task");
        check(taskList.getTaskByIndex(0) instanceof Todo, "load should restore todos");
        check(taskList.getTaskByIndex(1) instanceof Deadline, "load should restore deadlines");
        check(taskList.getTaskByIndex(2) instanceof Event, "load should restore events");
        check(!taskList.getTaskByIndex(0).isDone(), "load should restore incomplete status");
        check(taskList.getTaskByIndex(1).isDone(), "load should restore completed status");
    }

    /** Verifies that a missing file is treated as an empty first run. */
    private static void testMissingFileLoadsEmptyList(Path dataFile) {
        TaskList taskList = new Storage(dataFile).load(100);
        check(taskList.getSize() == 0, "missing data file should load an empty list");
    }

    /** Verifies that malformed records are not silently accepted. */
    private static void testMalformedRecordIsRejected(Path dataFile) throws IOException {
        Files.writeString(dataFile, "X | 0 | unknown task");
        try {
            new Storage(dataFile).load(100);
            throw new AssertionError("malformed record should be rejected");
        } catch (ArrodesException expected) {
            // Expected result.
        }
    }

    /** Fails a test with a useful message when a condition is false. */
    private static void check(boolean condition, String message) {
        if (!condition) {
            throw new AssertionError(message);
        }
    }
}
