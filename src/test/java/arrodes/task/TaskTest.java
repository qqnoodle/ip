package arrodes.task;

import arrodes.exception.ArrodesException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class TaskTest {

    @Test
    void getDescription_newTask_returnsDescription() {
        Task task = new Todo("read a book");
        assertEquals("read a book", task.getDescription());
    }

    @Test
    void getStatusIcon_newTask_returnsSpace() {
        Task task = new Todo("read a book");
        assertEquals(" ", task.getStatusIcon());
    }

    @Test
    void markAsDone_incompleteTask_taskIsMarkedDone() {
        Task task = new Todo("read a book");
        task.markAsDone();
        assertTrue(task.isDone());
        assertEquals("X", task.getStatusIcon());
    }

    @Test
    void markAsNotDone_completedTask_taskIsMarkedNotDone() {
        Task task = new Todo("read a book");
        task.markAsDone();
        task.markAsNotDone();
        assertFalse(task.isDone());
        assertEquals(" ", task.getStatusIcon());
    }

    @Test
    void toString_incompleteTask_containsBlankStatusIcon() {
        Task task = new Todo("read a book");
        assertTrue(task.toString().contains("[ ]"));
    }

    @Test
    void toString_completedTask_containsXStatusIcon() {
        Task task = new Todo("read a book");
        task.markAsDone();
        assertTrue(task.toString().contains("[X]"));
    }
}