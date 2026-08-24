package Arrodes.command;
import Arrodes.storage.Storage;
import Arrodes.task.TaskList;
import Arrodes.ui.Ui;

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
