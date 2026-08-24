package Arrodes.command;
import Arrodes.storage.Storage;
import Arrodes.task.TaskList;
import Arrodes.task.Task;
import Arrodes.ui.Ui;

public class DeleteCommand extends Command{
    private final int taskNumber;

    public DeleteCommand(int taskNumber) {
        this.taskNumber = taskNumber;
    }

    @Override
    public void execute(Ui ui, TaskList taskList, Storage storage) {
        Task task = taskList.getTaskByNumber(taskNumber);
        taskList.delete(taskNumber);
        storage.save(taskList);
        ui.showMessage("Erasing records of the task:\n");
        ui.showMessage(task + "\n");
        ui.showMessage(taskList.getSize() + " tasks remaining are being tracked");
    }
}
