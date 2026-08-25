package arrodes.task;
import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
/** Tests deadline validation, accessors, formatting, and completion display. */
class DeadlineTest {

    /** Rejects a deadline without a due date. */
    /** Verifies constructor nullDueBy throwsIllegalArgumentException. */
    @Test
    void constructor_nullDueBy_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class,
                () -> new Deadline("submit report", null));
    }

    /** Returns the due date supplied to a valid deadline. */
    /** Verifies getDueBy validDeadline returnsDueDate. */
    @Test
    void getDueBy_validDeadline_returnsDueDate() {
        LocalDateTime due = LocalDateTime.of(2025, 12, 31, 0, 0);
        Deadline deadline = new Deadline("submit report", due);
        assertEquals(due, deadline.getDueBy());
    }

    /** Formats a midnight deadline as a date without a time. */
    /** Verifies toString midnightDeadline showsDateOnly. */
    @Test
    void toString_midnightDeadline_showsDateOnly() {
        // Midnight is treated as date-only and should not show HH:mm
        LocalDateTime midnight = LocalDateTime.of(2025, 6, 15, 0, 0);
        Deadline deadline = new Deadline("submit report", midnight);
        String result = deadline.toString();
        assertTrue(result.startsWith("[D]"));
        assertTrue(result.contains("Jun 15 2025"));
        assertFalse(result.contains("00:00"));
    }

    /** Formats a non-midnight deadline with its time. */
    /** Verifies toString nonMidnightDeadline showsDateTime. */
    @Test
    void toString_nonMidnightDeadline_showsDateTime() {
        LocalDateTime due = LocalDateTime.of(2025, 6, 15, 14, 30);
        Deadline deadline = new Deadline("submit report", due);
        String result = deadline.toString();
        assertTrue(result.contains("Jun 15 2025 14:30"));
    }

    /** Displays the completed status icon for a finished deadline. */
    /** Verifies toString completedDeadline showsXStatusIcon. */
    @Test
    void toString_completedDeadline_showsXStatusIcon() {
        LocalDateTime due = LocalDateTime.of(2025, 6, 15, 0, 0);
        Deadline deadline = new Deadline("submit report", due);
        deadline.markAsDone();
        assertTrue(deadline.toString().contains("[X]"));
    }
}
