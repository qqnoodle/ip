package arrodes.task;

import arrodes.exception.ArrodesException;

import java.util.ArrayList;

/** Bounded, ordered collection of tasks remembered by Arrodes. */
public class TaskList {
    /** Maximum number of tasks this list can contain. */
    private final int capacity;
    /** Tasks in the order in which they were added. */
    private final ArrayList<Task> list = new ArrayList<>();

    /** Creates an empty list with the default capacity of 100 tasks. */
    public TaskList() {
        this.capacity = 100;
    }

    /** Creates an empty list with a chosen capacity.
     * @param capacity maximum number of tasks
     * @throws IllegalArgumentException if capacity is negative
     */
    public TaskList(int capacity) {
        if (capacity < 0) {
            throw new IllegalArgumentException("Task list capacity cannot be negative.");
        }
        this.capacity = capacity;
    }

    /** Returns the number of tasks currently stored.
     * @return current task count
     */
    public int getSize() {
        return list.size();
    }

    /** Returns whether no more tasks can be inserted.
     * @return {@code true} when the list has reached capacity
     */
    public boolean isFull() {
        return list.size() >= capacity;
    }

    /** Adds a valid task to the end of the list.
     * @param task task to add
     * @throws ArrodesException if the task is invalid or the list is full
     */
    public void insert(Task task) throws ArrodesException {
        if (task == null || task.getDescription() == null || task.getDescription().isBlank()) {
            throw new ArrodesException(ArrodesException.EMPTY_DESCRIPTION);
        }
        if (isFull()) {
            throw new ArrodesException(ArrodesException.TASK_LIST_FULL);
        }
        list.add(task);
    }

    /** Removes a task using its one-based display number.
     * @param itemNumber one-based task number
     * @throws ArrodesException if no task has that number
     */
    public void delete(int itemNumber) throws ArrodesException {
        int itemIndex = itemNumber - 1;
        if (itemIndex >= list.size() || itemIndex < 0) {
            throw new ArrodesException(ArrodesException.ITEM_NOT_IN_LIST);
        }
        list.remove(itemIndex);
    }

    /** Returns a task using its zero-based list index.
     * @param itemIndex zero-based index
     * @return task at the requested index
     * @throws ArrodesException if the index is outside the list
     */
    public Task getTaskByIndex(int itemIndex) {
        if (itemIndex >= list.size() || itemIndex < 0) {
            throw new ArrodesException(ArrodesException.ITEM_NOT_IN_LIST);
        }
        return list.get(itemIndex);
    }

    /** Returns a task using its one-based display number.
     * @param itemNumber one-based task number
     * @return task at the requested number
     * @throws ArrodesException if the number is outside the list
     */
    public Task getTaskByNumber(int itemNumber) {
        return getTaskByIndex(itemNumber - 1);
    }
}
