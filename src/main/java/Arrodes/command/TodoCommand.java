package arrodes.command;
import arrodes.storage.Storage;
import arrodes.task.TaskList;
import arrodes.task.Todo;
import arrodes.ui.Ui;

/** Adds a todo task to the task list. */
public class TodoCommand extends Command {
    private final String description;

    public TodoCommand(String description) {
        this.description = description;
    }

    @Override
    public void execute(Ui ui, TaskList taskList, Storage storage) {
        taskList.insert(new Todo(description));
        storage.save(taskList);
        ui.showMessage("Inscribing request: \n"
                + "   " + taskList.getTaskByNumber(taskList.getSize()).toString() + "\n"
                + taskList.getSize() + " tasks are being tracked");
    }
}
