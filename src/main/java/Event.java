import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

/** A task that takes place between two date-time values. */
public class Event extends Task {

    /** Starting date and time of the event. */
    private final LocalDateTime startAt;
    /** Ending date and time of the event. */
    private final LocalDateTime endAt;

    /** Creates an event with ISO local date-time boundaries. */
    public Event(String description, LocalDateTime startAt, LocalDateTime endAt) {
        super(description);
        if (startAt == null || endAt == null || endAt.isBefore(startAt)) {
            throw new IllegalArgumentException("Event times are invalid.");
        }
        this.startAt = startAt;
        this.endAt = endAt;
    }

    /**
     * Returns the event's starting-time text.
     *
     * @return starting date and time
     */
    public LocalDateTime getStartAt() {
        return startAt;
    }

    /**
     * Returns the event's ending-time text.
     *
     * @return ending date and time
     */
    public LocalDateTime getEndAt() {
        return endAt;
    }

    @Override
    public String toString() {
        DateTimeFormatter displayFormat = DateTimeFormatter.ofPattern("MMM dd yyyy HH:mm", Locale.ENGLISH);
        return String.format("[E]%s (from: %s to %s)", super.toString(),
                startAt.format(displayFormat), endAt.format(displayFormat));
    }
}
