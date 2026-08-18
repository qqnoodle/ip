import java.util.ArrayList;

public class TaskList {
    private int capacity;
    ArrayList<Task> list = new ArrayList<>();

    public TaskList() {
        //Default capacity 100
        this.capacity = 100;
    }
    public TaskList(int capacity) {
        this.capacity = capacity;
    }

    public int getSize() {
        return list.size();
    }

    public boolean isFull() {
        return list.size() == capacity;
    }

    public void insert(Task task) throws ArrodesException{
        if (isFull()) throw new ArrodesException(ArrodesException.TASK_LIST_FULL);
        list.add(task);
    }

    public void delete(int itemNumber) throws ArrodesException{
        int itemIndex = itemNumber - 1;
        if ((itemIndex) >= list.size() || itemIndex < 0) throw new ArrodesException(ArrodesException.ITEM_NOT_IN_LIST);
        list.remove(itemIndex);
    }

    public Task getTaskByIndex(int itemIndex) {
        if ((itemIndex) >= list.size() || itemIndex < 0) throw new ArrodesException(ArrodesException.ITEM_NOT_IN_LIST);
        return list.get(itemIndex);
    }

    public Task getTaskByNumber(int itemNumber) {
        return getTaskByIndex(itemNumber - 1);
    }
}
