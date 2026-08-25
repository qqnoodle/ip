package arrodes.task;

import arrodes.exception.ArrodesException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

/** Tests common task description, status, mutation, and display behavior. */
class TaskTest {

    /** Returns the description supplied to a new task. */
    /** Verifies getDescription newTask returnsDescription. */
    @Test
    void getDescription_newTask_returnsDescription() {
        Task task = new Todo("read a book");
        assertEquals("read a book", task.getDescription());
    }

    /** Uses a blank status icon for a new task. */
    /** Verifies getStatusIcon newTask returnsSpace. */
    @Test
    void getStatusIcon_newTask_returnsSpace() {
        Task task = new Todo("read a book");
        assertEquals(" ", task.getStatusIcon());
    }

    /** Marks an incomplete task as done. */
    /** Verifies markAsDone incompleteTask taskIsMarkedDone. */
    @Test
    void markAsDone_incompleteTask_taskIsMarkedDone() {
        Task task = new Todo("read a book");
        task.markAsDone();
        assertTrue(task.isDone());
        assertEquals("X", task.getStatusIcon());
    }

    /** Reopens a completed task. */
    /** Verifies markAsNotDone completedTask taskIsMarkedNotDone. */
    @Test
    void markAsNotDone_completedTask_taskIsMarkedNotDone() {
        Task task = new Todo("read a book");
        task.markAsDone();
        task.markAsNotDone();
        assertFalse(task.isDone());
        assertEquals(" ", task.getStatusIcon());
    }

    /** Includes the incomplete status in task text. */
    /** Verifies toString incompleteTask containsBlankStatusIcon. */
    @Test
    void toString_incompleteTask_containsBlankStatusIcon() {
        Task task = new Todo("read a book");
        assertTrue(task.toString().contains("[ ]"));
    }

    /** Includes the completed status in task text. */
    /** Verifies toString completedTask containsXStatusIcon. */
    @Test
    void toString_completedTask_containsXStatusIcon() {
        Task task = new Todo("read a book");
        task.markAsDone();
        assertTrue(task.toString().contains("[X]"));
    }
}
