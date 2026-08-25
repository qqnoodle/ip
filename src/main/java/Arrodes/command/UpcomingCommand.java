package arrodes.command;

import arrodes.storage.Storage;
import arrodes.task.*;
import arrodes.ui.Ui;
import javax.swing.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

/** Command that displays deadlines and events relevant to a date or time. */
public class UpcomingCommand extends Command {
    /** Date or date-time against which tasks are matched. */
    private final LocalDateTime on;
    /** Whether the query explicitly included a time. */
    private final boolean includesTime;
    /** Creates an upcoming-task query.
     * @param on target date or date-time
     * @param includesTime whether the query includes a time
     */
    public UpcomingCommand(LocalDateTime on, boolean includesTime) {
        this.on = on;
        this.includesTime = includesTime;
    }

    /** Displays matching deadlines and events.
     * @param ui interface used for output
     * @param taskList list to search
     * @param storage unused storage
     */
    @Override
    public void execute(Ui ui, TaskList taskList, Storage storage) {
        boolean dateOnly = !includesTime;
        LocalDate targetDate = on.toLocalDate();
        LocalDateTime dayEnd = targetDate.atTime(LocalTime.MAX);
        DateTimeFormatter displayFormat = DateTimeFormatter.ofPattern(
                dateOnly ? "MMM dd yyyy" : "MMM dd yyyy HH:mm", Locale.ENGLISH);
        LocalDateTime deadlineCutoff = dateOnly ? dayEnd : on;
        boolean found = false;

        ui.showMessage("Arrodes recalls requests for " + on.format(displayFormat) + ":");
        for (int i = 0; i < taskList.getSize(); i++) {
            Task task = taskList.getTaskByIndex(i);
            boolean matches = false;
            if (task instanceof Deadline deadline) {
                matches = !deadline.getDueBy().isAfter(deadlineCutoff);
            } else if (task instanceof Event event) {
                matches = dateOnly
                        ? !event.getStartAt().toLocalDate().isAfter(targetDate)
                        && !event.getEndAt().toLocalDate().isBefore(targetDate)
                        : !event.getStartAt().isAfter(on) && !event.getEndAt().isBefore(on);
            }
            if (matches) {
                ui.showMessage((i + 1) + "." + task);
                found = true;
            }
        }
        if (!found) {
            ui.showMessage("Arrodes found no deadlines or events for that date or time.");
        }
    }
}
