public class Event extends Task {

    private final String startAt;
    private final String endAt;

    public Event (String description, String startAt, String endAt) {
        super(description);
        this.startAt = startAt;
        this.endAt = endAt;
    }

    /**
     * Returns the event's starting-time text.
     *
     * @return starting-time text
     */
    public String getStartAt() {
        return startAt;
    }

    /**
     * Returns the event's ending-time text.
     *
     * @return ending-time text
     */
    public String getEndAt() {
        return endAt;
    }

    @Override
    public String toString() {
        return String.format("[E]%s (from: %s to %s)", super.toString(), startAt, endAt);
    }
}
