import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Comparator;
import java.util.List;
import java.time.LocalDateTime;

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
            testEscapedFieldsRoundTrip(temporaryDirectory.resolve("escaped.txt"));
            testDateAndTimeValuesAreTyped(temporaryDirectory.resolve("typed.txt"));
            testCapacityOverflowIsRejected(temporaryDirectory.resolve("full.txt"));
            testSaveFailureDoesNotReplaceDirectory(temporaryDirectory.resolve("directory-target"));
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

        Deadline deadline = new Deadline("return book", LocalDateTime.of(2026, 6, 6, 0, 0));
        deadline.markAsDone();
        taskList.insert(deadline);
        taskList.insert(new Event("project meeting", LocalDateTime.of(2026, 8, 6, 14, 0),
                LocalDateTime.of(2026, 8, 6, 16, 0)));

        new Storage(dataFile).save(taskList);

        List<String> actual = Files.readAllLines(dataFile);
        List<String> expected = List.of(
                "T | 0 | read book",
                "D | 1 | return book | 2026-06-06",
                "E | 0 | project meeting | 2026-08-06T14:00 | 2026-08-06T16:00");
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
                "D | 1 | return book | 2026-06-06",
                "E | 0 | project meeting | 2026-08-06T14:00 | 2026-08-06T16:00"));

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
        expectLoadFailure(dataFile, 100, "malformed record should be rejected");
        Files.writeString(dataFile, "T | 2 | invalid status");
        expectLoadFailure(dataFile, 100, "invalid status should be rejected");
        Files.writeString(dataFile, "T | 0 | unfinished\\");
        expectLoadFailure(dataFile, 100, "unfinished escape should be rejected");
        Files.writeString(dataFile, "D | 0 | missing due date");
        expectLoadFailure(dataFile, 100, "missing deadline field should be rejected");
        Files.writeString(dataFile, "D | 0 | impossible date | 2026-02-31");
        expectLoadFailure(dataFile, 100, "impossible calendar date should be rejected");
        Files.writeString(dataFile, "E | 0 | reversed event | 2026-09-05T10:00 | 2026-09-05T09:00");
        expectLoadFailure(dataFile, 100, "event ending before starting should be rejected");
    }

    /** Verifies that descriptions and time fields may contain storage delimiters. */
    private static void testEscapedFieldsRoundTrip(Path dataFile) throws IOException {
        TaskList original = new TaskList();
        original.insert(new Todo("read | write \\ revise"));
        original.insert(new Deadline("submit | report", LocalDateTime.of(2026, 8, 7, 0, 0)));
        Storage storage = new Storage(dataFile);
        storage.save(original);

        TaskList loaded = storage.load(100);
        check(loaded.getTaskByIndex(0).getDescription().equals("read | write \\ revise"),
                "load should preserve escaped todo text");
        check(((Deadline) loaded.getTaskByIndex(1)).getDueBy().equals(LocalDateTime.of(2026, 8, 7, 0, 0)),
                "load should preserve deadline date");
    }

    /** Verifies that date and time fields are exposed as java.time values. */
    private static void testDateAndTimeValuesAreTyped(Path dataFile) throws IOException {
        TaskList original = new TaskList();
        original.insert(new Deadline("pay bill", LocalDateTime.of(2026, 9, 1, 0, 0)));
        original.insert(new Event("appointment", LocalDateTime.of(2026, 9, 2, 9, 30),
                LocalDateTime.of(2026, 9, 2, 10, 0)));
        Storage storage = new Storage(dataFile);
        storage.save(original);
        TaskList loaded = storage.load(10);
        check(((Deadline) loaded.getTaskByIndex(0)).getDueBy().equals(LocalDateTime.of(2026, 9, 1, 0, 0)),
                "deadline should expose a LocalDateTime");
        check(((Event) loaded.getTaskByIndex(1)).getStartAt().equals(LocalDateTime.of(2026, 9, 2, 9, 30)),
                "event should expose a LocalDateTime");

        Files.write(dataFile, List.of(
                "D | 0 | timed deadline | 2026-09-03T17:30",
                "E | 0 | date-only event | 2026-09-04 | 2026-09-05"));
        TaskList mixedFormats = storage.load(10);
        check(((Deadline) mixedFormats.getTaskByIndex(0)).getDueBy()
                        .equals(LocalDateTime.of(2026, 9, 3, 17, 30)),
                "deadline should accept a date-time");
        check(((Event) mixedFormats.getTaskByIndex(1)).getStartAt()
                        .equals(LocalDateTime.of(2026, 9, 4, 0, 0)),
                "event should accept date-only values");
    }

    /** Verifies that a file with more records than the configured limit is rejected. */
    private static void testCapacityOverflowIsRejected(Path dataFile) throws IOException {
        Files.write(dataFile, List.of("T | 0 | first", "T | 0 | second"));
        expectLoadFailure(dataFile, 1, "capacity overflow should be rejected");
    }

    /** Verifies that a failed save cannot replace a directory with a file. */
    private static void testSaveFailureDoesNotReplaceDirectory(Path dataFile) throws IOException {
        Files.createDirectory(dataFile);
        try {
            new Storage(dataFile).save(new TaskList());
            throw new AssertionError("saving to a directory should fail");
        } catch (ArrodesException expected) {
            check(Files.isDirectory(dataFile), "failed save should leave the existing target intact");
        }
    }

    /** Verifies that invalid storage input consistently raises the storage error. */
    private static void expectLoadFailure(Path dataFile, int capacity, String message) {
        try {
            new Storage(dataFile).load(capacity);
            throw new AssertionError(message);
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
