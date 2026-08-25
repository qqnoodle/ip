package arrodes.task;

import arrodes.exception.ArrodesException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class TaskListTest {

    private TaskList taskList;

    @BeforeEach
    void setUp() {
        taskList = new TaskList();
    }


    @Test
    void constructor_negativeCapacity_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> new TaskList(-1));
    }

    @Test
    void constructor_zeroCapacity_createsEmptyFullList() {
        TaskList zeroList = new TaskList(0);
        assertTrue(zeroList.isFull());
        assertEquals(0, zeroList.getSize());
    }


    @Test
    void getSize_emptyList_returnsZero() {
        assertEquals(0, taskList.getSize());
    }

    @Test
    void isFull_emptyDefaultList_returnsFalse() {
        assertFalse(taskList.isFull());
    }

    @Test
    void isFull_listAtCapacity_returnsTrue() throws ArrodesException {
        TaskList small = new TaskList(1);
        small.insert(new Todo("only task"));
        assertTrue(small.isFull());
    }


    @Test
    void insert_validTask_increasesSize() throws ArrodesException {
        taskList.insert(new Todo("walk the dog"));
        assertEquals(1, taskList.getSize());
    }

    @Test
    void insert_nullTask_throwsArrodesException() {
        assertThrows(ArrodesException.class, () -> taskList.insert(null));
    }

    @Test
    void insert_taskWithBlankDescription_throwsArrodesException() {
        assertThrows(ArrodesException.class, () -> taskList.insert(new Todo("   ")));
    }

    @Test
    void insert_whenFull_throwsArrodesException() throws ArrodesException {
        TaskList small = new TaskList(1);
        small.insert(new Todo("first task"));
        assertThrows(ArrodesException.class, () -> small.insert(new Todo("second task")));
    }

    @Test
    void insert_multipleValidTasks_allInserted() throws ArrodesException {
        taskList.insert(new Todo("task one"));
        taskList.insert(new Todo("task two"));
        taskList.insert(new Todo("task three"));
        assertEquals(3, taskList.getSize());
    }


    @Test
    void delete_validItemNumber_decreasesSize() throws ArrodesException {
        taskList.insert(new Todo("delete me"));
        taskList.delete(1);
        assertEquals(0, taskList.getSize());
    }

    @Test
    void delete_itemNumberZero_throwsArrodesException() throws ArrodesException {
        taskList.insert(new Todo("a task"));
        assertThrows(ArrodesException.class, () -> taskList.delete(0));
    }

    @Test
    void delete_itemNumberBeyondSize_throwsArrodesException() throws ArrodesException {
        taskList.insert(new Todo("a task"));
        assertThrows(ArrodesException.class, () -> taskList.delete(2));
    }

    @Test
    void delete_onlyTask_listBecomesEmpty() throws ArrodesException {
        taskList.insert(new Todo("sole task"));
        taskList.delete(1);
        assertEquals(0, taskList.getSize());
    }

    @Test
    void delete_middleTask_remainingTasksShiftCorrectly() throws ArrodesException {
        taskList.insert(new Todo("first"));
        taskList.insert(new Todo("second"));
        taskList.insert(new Todo("third"));
        taskList.delete(2); // removes "second"
        assertEquals(2, taskList.getSize());
        assertEquals("first", taskList.getTaskByNumber(1).getDescription());
        assertEquals("third", taskList.getTaskByNumber(2).getDescription());
    }


    @Test
    void getTaskByIndex_validIndex_returnsCorrectTask() throws ArrodesException {
        taskList.insert(new Todo("alpha"));
        taskList.insert(new Todo("beta"));
        assertEquals("beta", taskList.getTaskByIndex(1).getDescription());
    }

    @Test
    void getTaskByIndex_negativeIndex_throwsArrodesException() throws ArrodesException {
        taskList.insert(new Todo("a task"));
        assertThrows(ArrodesException.class, () -> taskList.getTaskByIndex(-1));
    }

    @Test
    void getTaskByIndex_indexEqualToSize_throwsArrodesException() throws ArrodesException {
        taskList.insert(new Todo("a task"));
        assertThrows(ArrodesException.class, () -> taskList.getTaskByIndex(1));
    }


    @Test
    void getTaskByNumber_validNumber_returnsCorrectTask() throws ArrodesException {
        taskList.insert(new Todo("alpha"));
        taskList.insert(new Todo("beta"));
        assertEquals("alpha", taskList.getTaskByNumber(1).getDescription());
        assertEquals("beta",  taskList.getTaskByNumber(2).getDescription());
    }

    @Test
    void getTaskByNumber_numberLargerThanSize_throwsArrodesException() throws ArrodesException {
        taskList.insert(new Todo("a task"));
        assertThrows(ArrodesException.class, () -> taskList.getTaskByNumber(2));
    }

    @Test
    void getTaskByNumber_numberZero_throwsArrodesException() {
        assertThrows(ArrodesException.class, () -> taskList.getTaskByNumber(0));
    }


    @Test
    void insert_mixedTaskTypes_allStoredCorrectly() throws ArrodesException {
        LocalDateTime due   = LocalDateTime.of(2025, 12, 1, 0, 0);
        LocalDateTime start = LocalDateTime.of(2025, 12, 5, 9, 0);
        LocalDateTime end   = LocalDateTime.of(2025, 12, 5, 17, 0);

        taskList.insert(new Todo("todo task"));
        taskList.insert(new Deadline("deadline task", due));
        taskList.insert(new Event("event task", start, end));

        assertEquals(3, taskList.getSize());
        assertInstanceOf(Todo.class,     taskList.getTaskByNumber(1));
        assertInstanceOf(Deadline.class, taskList.getTaskByNumber(2));
        assertInstanceOf(Event.class,    taskList.getTaskByNumber(3));
    }
}
