package arrodes.command;
import arrodes.exception.ArrodesException;
import arrodes.storage.Storage;
import arrodes.task.TaskList;
import arrodes.task.Event;
import arrodes.ui.Ui;

import java.time.LocalDateTime;

public class EventCommand extends Command{
    private final String description;
    private final LocalDateTime from;
    private final LocalDateTime to;
    private final boolean startIncludesTime;
    private  final boolean endIncludesTime;

    public EventCommand(String description, LocalDateTime from, LocalDateTime to, boolean startIncludesTime, boolean endIncludesTime) {
        this.description = description;
        this.from = from;
        this.to = to;
        this.startIncludesTime = startIncludesTime;
        this.endIncludesTime = endIncludesTime;
    }

    @Override
    public void execute(Ui ui, TaskList taskList, Storage storage) {
        if (to.isBefore(from)) {
            throw new ArrodesException(ArrodesException.INVALID_EVENT_TIME);
        }
        taskList.insert(new Event(description, from, to, startIncludesTime, endIncludesTime));
        storage.save(taskList);
        System.out.println("Inscribing request: \n"
                + "   " + taskList.getTaskByNumber(taskList.getSize()).toString() + "\n"
                + taskList.getSize() + " tasks are being tracked");
    }
}
