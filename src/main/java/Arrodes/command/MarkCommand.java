package Arrodes.command;
import Arrodes.storage.Storage;
import Arrodes.task.TaskList;
import Arrodes.task.Task;
import Arrodes.ui.Ui;

public class MarkCommand extends Command{
    private final int taskNumber;

    public MarkCommand(int taskNumber) {
       this.taskNumber = taskNumber;
    }

    @Override
    public void execute(Ui ui, TaskList taskList, Storage storage) {
        Task task = taskList.getTaskByNumber(taskNumber);
        task.markAsDone();
        storage.save(taskList);
        ui.showMessage("A worthy task! Arrodes has marked it as done:");
        ui.showMessage("  " + task);
    }
}
