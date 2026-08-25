package arrodes.task;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/** Tests todo-specific display formatting. */
class TodoTest {

    /** Displays the todo type and incomplete status. */
    /** Verifies toString incompleteTodo hasCorrectPrefix. */
    @Test
    void toString_incompleteTodo_hasCorrectPrefix() {
        Todo todo = new Todo("buy groceries");
        assertEquals("[T][ ] buy groceries", todo.toString());
    }

    /** Displays the todo type and completed status. */
    /** Verifies toString completedTodo hasCorrectPrefix. */
    @Test
    void toString_completedTodo_hasCorrectPrefix() {
        Todo todo = new Todo("buy groceries");
        todo.markAsDone();
        assertEquals("[T][X] buy groceries", todo.toString());
    }
}
