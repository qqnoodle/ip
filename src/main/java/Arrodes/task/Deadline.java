package arrodes.task;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

/** A task that must be completed by a date, optionally including a time. */
public class Deadline extends Task {

    /** Date by which this task should be completed. */
    private final LocalDateTime dueBy;

    /** Creates a deadline with an ISO date or date-time. */
    public Deadline(String description, LocalDateTime dueBy) {
        super(description);
        if (dueBy == null) {
            throw new IllegalArgumentException("Deadline date cannot be null.");
        }
        this.dueBy = dueBy;
    }

    /**
     * Returns the deadline's due-date text.
     *
     * @return due date and time
     */
    public LocalDateTime getDueBy() {
        return dueBy;
    }

    @Override
    public String toString() {
        return String.format("[D]%s (by: %s)", super.toString(), formatDueBy());
    }

    /** Displays midnight deadlines as dates while retaining non-midnight times. */
    private String formatDueBy() {
        return dueBy.toLocalTime().equals(java.time.LocalTime.MIDNIGHT)
                ? dueBy.format(DateTimeFormatter.ofPattern("MMM dd yyyy", Locale.ENGLISH))
                : dueBy.format(DateTimeFormatter.ofPattern("MMM dd yyyy HH:mm", Locale.ENGLISH));
    }
}
