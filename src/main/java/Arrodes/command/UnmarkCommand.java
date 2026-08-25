package arrodes.command;
import arrodes.storage.Storage;
import arrodes.task.TaskList;
import arrodes.task.Task;
import arrodes.ui.Ui;

public class UnmarkCommand extends Command{
    private final int taskNumber;

    public UnmarkCommand(int taskNumber) {
        this.taskNumber = taskNumber;
    }

    @Override
    public void execute(Ui ui, TaskList taskList, Storage storage) {
        Task task = taskList.getTaskByNumber(taskNumber);
        task.markAsNotDone();
        storage.save(taskList);
        ui.showMessage("As you decree, arrodes has marked this task as not done yet:");
        ui.showMessage("  " + task);
    }
}
