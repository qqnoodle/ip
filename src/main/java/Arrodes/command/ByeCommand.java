package arrodes.command;
import arrodes.storage.Storage;
import arrodes.task.TaskList;
import arrodes.ui.Ui;

public class ByeCommand extends Command{
    public ByeCommand() {
    }

    @Override
    public boolean isExit() {
        return true;
    }
    @Override
    public void execute(Ui ui, TaskList taskList, Storage storage) {
        ui.showOnExitMessage();
    }
}
