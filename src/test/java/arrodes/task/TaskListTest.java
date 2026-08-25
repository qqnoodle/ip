package arrodes.task;

import arrodes.exception.ArrodesException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

/** Tests task-list capacity, mutation, indexing, and retrieval behavior. */
class TaskListTest {

    private TaskList taskList;

    /** Creates a fresh default-capacity list for each test. */
    @BeforeEach
    void setUp() {
        taskList = new TaskList();
    }


    /** Rejects a negative list capacity. */
    /** Verifies constructor negativeCapacity throwsIllegalArgumentException. */
    @Test
    void constructor_negativeCapacity_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> new TaskList(-1));
    }

    /** Allows a zero-capacity list and reports it as full. */
    /** Verifies constructor zeroCapacity createsEmptyFullList. */
    @Test
    void constructor_zeroCapacity_createsEmptyFullList() {
        TaskList zeroList = new TaskList(0);
        assertTrue(zeroList.isFull());
        assertEquals(0, zeroList.getSize());
    }


    /** Returns zero for a new list. */
    /** Verifies getSize emptyList returnsZero. */
    @Test
    void getSize_emptyList_returnsZero() {
        assertEquals(0, taskList.getSize());
    }

    /** Reports that a new default list is not full. */
    /** Verifies isFull emptyDefaultList returnsFalse. */
    @Test
    void isFull_emptyDefaultList_returnsFalse() {
        assertFalse(taskList.isFull());
    }

    /** Reports full when a list reaches its capacity. */
    /** Verifies isFull listAtCapacity returnsTrue. */
    @Test
    void isFull_listAtCapacity_returnsTrue() throws ArrodesException {
        TaskList small = new TaskList(1);
        small.insert(new Todo("only task"));
        assertTrue(small.isFull());
    }


    /** Inserts a valid task and increases the size. */
    /** Verifies insert validTask increasesSize. */
    @Test
    void insert_validTask_increasesSize() throws ArrodesException {
        taskList.insert(new Todo("walk the dog"));
        assertEquals(1, taskList.getSize());
    }

    /** Rejects a null task. */
    /** Verifies insert nullTask throwsArrodesException. */
    @Test
    void insert_nullTask_throwsArrodesException() {
        assertThrows(ArrodesException.class, () -> taskList.insert(null));
    }

    /** Rejects a task with a blank description. */
    /** Verifies insert taskWithBlankDescription throwsArrodesException. */
    @Test
    void insert_taskWithBlankDescription_throwsArrodesException() {
        assertThrows(ArrodesException.class, () -> taskList.insert(new Todo("   ")));
    }

    /** Rejects insertion after the list reaches capacity. */
    /** Verifies insert whenFull throwsArrodesException. */
    @Test
    void insert_whenFull_throwsArrodesException() throws ArrodesException {
        TaskList small = new TaskList(1);
        small.insert(new Todo("first task"));
        assertThrows(ArrodesException.class, () -> small.insert(new Todo("second task")));
    }

    /** Inserts several valid tasks in order. */
    /** Verifies insert multipleValidTasks allInserted. */
    @Test
    void insert_multipleValidTasks_allInserted() throws ArrodesException {
        taskList.insert(new Todo("task one"));
        taskList.insert(new Todo("task two"));
        taskList.insert(new Todo("task three"));
        assertEquals(3, taskList.getSize());
    }


    /** Deletes a valid task and decreases the size. */
    /** Verifies delete validItemNumber decreasesSize. */
    @Test
    void delete_validItemNumber_decreasesSize() throws ArrodesException {
        taskList.insert(new Todo("delete me"));
        taskList.delete(1);
        assertEquals(0, taskList.getSize());
    }

    /** Rejects display number zero. */
    /** Verifies delete itemNumberZero throwsArrodesException. */
    @Test
    void delete_itemNumberZero_throwsArrodesException() throws ArrodesException {
        taskList.insert(new Todo("a task"));
        assertThrows(ArrodesException.class, () -> taskList.delete(0));
    }

    /** Rejects a display number beyond the list size. */
    /** Verifies delete itemNumberBeyondSize throwsArrodesException. */
    @Test
    void delete_itemNumberBeyondSize_throwsArrodesException() throws ArrodesException {
        taskList.insert(new Todo("a task"));
        assertThrows(ArrodesException.class, () -> taskList.delete(2));
    }

    /** Leaves an empty list after deleting its only task. */
    /** Verifies delete onlyTask listBecomesEmpty. */
    @Test
    void delete_onlyTask_listBecomesEmpty() throws ArrodesException {
        taskList.insert(new Todo("sole task"));
        taskList.delete(1);
        assertEquals(0, taskList.getSize());
    }

    /** Shifts later tasks after deleting a middle task. */
    /** Verifies delete middleTask remainingTasksShiftCorrectly. */
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


    /** Retrieves a task by zero-based index. */
    /** Verifies getTaskByIndex validIndex returnsCorrectTask. */
    @Test
    void getTaskByIndex_validIndex_returnsCorrectTask() throws ArrodesException {
        taskList.insert(new Todo("alpha"));
        taskList.insert(new Todo("beta"));
        assertEquals("beta", taskList.getTaskByIndex(1).getDescription());
    }

    /** Rejects a negative zero-based index. */
    /** Verifies getTaskByIndex negativeIndex throwsArrodesException. */
    @Test
    void getTaskByIndex_negativeIndex_throwsArrodesException() throws ArrodesException {
        taskList.insert(new Todo("a task"));
        assertThrows(ArrodesException.class, () -> taskList.getTaskByIndex(-1));
    }

    /** Rejects an index equal to the list size. */
    /** Verifies getTaskByIndex indexEqualToSize throwsArrodesException. */
    @Test
    void getTaskByIndex_indexEqualToSize_throwsArrodesException() throws ArrodesException {
        taskList.insert(new Todo("a task"));
        assertThrows(ArrodesException.class, () -> taskList.getTaskByIndex(1));
    }


    /** Retrieves tasks by one-based display number. */
    /** Verifies getTaskByNumber validNumber returnsCorrectTask. */
    @Test
    void getTaskByNumber_validNumber_returnsCorrectTask() throws ArrodesException {
        taskList.insert(new Todo("alpha"));
        taskList.insert(new Todo("beta"));
        assertEquals("alpha", taskList.getTaskByNumber(1).getDescription());
        assertEquals("beta",  taskList.getTaskByNumber(2).getDescription());
    }

    /** Rejects a display number beyond the list size. */
    /** Verifies getTaskByNumber numberLargerThanSize throwsArrodesException. */
    @Test
    void getTaskByNumber_numberLargerThanSize_throwsArrodesException() throws ArrodesException {
        taskList.insert(new Todo("a task"));
        assertThrows(ArrodesException.class, () -> taskList.getTaskByNumber(2));
    }

    /** Rejects display number zero. */
    /** Verifies getTaskByNumber numberZero throwsArrodesException. */
    @Test
    void getTaskByNumber_numberZero_throwsArrodesException() {
        assertThrows(ArrodesException.class, () -> taskList.getTaskByNumber(0));
    }


    /** Preserves todo, deadline, and event types in one list. */
    /** Verifies insert mixedTaskTypes allStoredCorrectly. */
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
