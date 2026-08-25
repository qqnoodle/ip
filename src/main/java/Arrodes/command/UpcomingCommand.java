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

public class UpcomingCommand extends Command {
    private final LocalDateTime on;
    private final boolean includesTime;
    public UpcomingCommand(LocalDateTime on, boolean includesTime) {
        this.on = on;
        this.includesTime = includesTime;
    }

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
