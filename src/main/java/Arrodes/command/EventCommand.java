package arrodes.command;
import arrodes.exception.ArrodesException;
import arrodes.storage.Storage;
import arrodes.task.TaskList;
import arrodes.task.Event;
import arrodes.ui.Ui;

import java.time.LocalDateTime;

/** Command that adds an event task and saves the updated list. */
public class EventCommand extends Command{
    /** Text describing the event. */
    private final String description;
    /** Event start. */
    private final LocalDateTime from;
    /** Event end. */
    private final LocalDateTime to;
    /** Whether the input included a time at the start. */
    private final boolean startIncludesTime;
    /** Whether the input included a time at the end. */
    private final boolean endIncludesTime;

    /** Creates a command for adding an event.
     * @param description event description
     * @param from event start
     * @param to event end
     * @param startIncludesTime whether the start included a time
     * @param endIncludesTime whether the end included a time
     */
    public EventCommand(String description, LocalDateTime from, LocalDateTime to, boolean startIncludesTime, boolean endIncludesTime) {
        this.description = description;
        this.from = from;
        this.to = to;
        this.startIncludesTime = startIncludesTime;
        this.endIncludesTime = endIncludesTime;
    }

    /** Adds the event, persists it, and reports the new task count.
     * @param ui interface used for output
     * @param taskList list to update
     * @param storage persistence layer
     */
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
