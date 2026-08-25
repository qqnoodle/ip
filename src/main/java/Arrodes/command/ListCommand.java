package arrodes.command;
import arrodes.storage.Storage;
import arrodes.task.TaskList;
import arrodes.ui.Ui;

public class ListCommand extends Command{
    public ListCommand() {
    }

    @Override
    public void execute(Ui ui, TaskList taskList, Storage storage) {
        ui.showMessage("arrodes recalls your requests:");
        for (int i = 0; i < taskList.getSize(); i++) {
            ui.showMessage((i + 1) + "." + taskList.getTaskByIndex(i).toString());
        }
    }
}
