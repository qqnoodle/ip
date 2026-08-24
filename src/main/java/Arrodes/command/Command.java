package Arrodes.command;
import Arrodes.storage.Storage;
import Arrodes.task.TaskList;
import Arrodes.ui.Ui;

public abstract class Command {
    public Command() {
    }
    public boolean isExit() {
        return false;
    }
    public abstract void execute(Ui ui, TaskList taskList, Storage storage);
}
