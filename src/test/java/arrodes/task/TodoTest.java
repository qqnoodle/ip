package arrodes.task;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class TodoTest {

    @Test
    void toString_incompleteTodo_hasCorrectPrefix() {
        Todo todo = new Todo("buy groceries");
        assertEquals("[T][ ] buy groceries", todo.toString());
    }

    @Test
    void toString_completedTodo_hasCorrectPrefix() {
        Todo todo = new Todo("buy groceries");
        todo.markAsDone();
        assertEquals("[T][X] buy groceries", todo.toString());
    }
}