package arrodes.command;
import arrodes.storage.Storage;
import arrodes.task.TaskList;
import arrodes.ui.Ui;

public abstract class Command {
    public Command() {
    }
    public boolean isExit() {
        return false;
    }
    public abstract void execute(Ui ui, TaskList taskList, Storage storage);
}
