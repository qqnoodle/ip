package Arrodes.command;
import Arrodes.storage.Storage;
import Arrodes.task.TaskList;
import Arrodes.task.Task;
import Arrodes.ui.Ui;

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
        ui.showMessage("As you decree, Arrodes has marked this task as not done yet:");
        ui.showMessage("  " + task);
    }
}
