package arrodes.command;
import arrodes.storage.Storage;
import arrodes.task.TaskList;
import arrodes.ui.Ui;

/** Represents one parsed user command. */
public abstract class Command {
    /** Creates a command. */
    public Command() {
    }
    /** Returns whether executing this command should terminate Arrodes.
     * @return {@code true} only for commands that exit the application
     */
    public boolean isExit() {
        return false;
    }
    /** Applies this command to the current task list and persistence layer.
     * @param ui interface used for user-facing output
     * @param taskList current tasks
     * @param storage persistence layer for saving changes
     */
    public abstract void execute(Ui ui, TaskList taskList, Storage storage);
}
