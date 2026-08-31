package arrodes.task;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;


/** Tests common task description, status, mutation, and display behavior. */
class TaskTest {

    /** Returns the description supplied to a new task. */
    @Test
    void getDescription_newTask_returnsDescription() {
        Task task = new Todo("read a book");
        assertEquals("read a book", task.getDescription());
    }

    /** Uses a blank status icon for a new task. */
    @Test
    void getStatusIcon_newTask_returnsSpace() {
        Task task = new Todo("read a book");
        assertEquals(" ", task.getStatusIcon());
    }

    /** Marks an incomplete task as done. */
    @Test
    void markAsDone_incompleteTask_taskIsMarkedDone() {
        Task task = new Todo("read a book");
        task.markAsDone();
        assertTrue(task.isDone());
        assertEquals("X", task.getStatusIcon());
    }

    /** Reopens a completed task. */
    @Test
    void markAsNotDone_completedTask_taskIsMarkedNotDone() {
        Task task = new Todo("read a book");
        task.markAsDone();
        task.markAsNotDone();
        assertFalse(task.isDone());
        assertEquals(" ", task.getStatusIcon());
    }

    /** Includes the incomplete status in task text. */
    @Test
    void toString_incompleteTask_containsBlankStatusIcon() {
        Task task = new Todo("read a book");
        assertTrue(task.toString().contains("[ ]"));
    }

    /** Includes the completed status in task text. */
    @Test
    void toString_completedTask_containsXStatusIcon() {
        Task task = new Todo("read a book");
        task.markAsDone();
        assertTrue(task.toString().contains("[X]"));
    }
}
