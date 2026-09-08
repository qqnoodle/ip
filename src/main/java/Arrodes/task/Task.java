package arrodes.task;

/**
 * Represents one task remembered by Arrodes.
 */
public class Task {
    /** Text describing what needs to be done. */
    private final String description;

    /** Whether the task has been completed. */
    private boolean isDone;
    /** Tag associated with the task. */
    private String tag = "";

    /**
     * Creates an incomplete task with the given description.
     *
     * @param description text describing the task
     */
    public Task(String description) {
        this.description = description;
        this.isDone = false;
    }

    /**
     * Returns the task's description.
     *
     * @return task description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Returns the status symbol displayed in task lists.
     *
     * @return {@code X} for a completed task, or a blank space otherwise
     */
    public String getStatusIcon() {
        return isDone ? "X" : " ";
    }

    /**
     * Returns whether this task has been completed.
     *
     * @return true if the task is complete
     */
    public boolean isDone() {
        return isDone;
    }

    /** Marks this task as completed. */
    public void markAsDone() {
        isDone = true;
    }

    /** Marks this task as incomplete. */
    public void markAsNotDone() {
        isDone = false;
    }
    /**
     * Associates the given tag with this task.
     *
     * @param tag tag to associate with the task
     */
    public void tagWith(String tag) {
        this.tag = tag;
    }

    /**
     * Returns the tag associated with this task.
     *
     * @return task tag, or an empty string if the task is not tagged
     */
    public String getTag() {
        return tag;
    }

    /**
     * Returns a formatted display representation of this task.
     *
     * @return task status and description, followed by the tag prefixed with {@code #} when present
     */
    @Override
    public String toString() {
        boolean hasTags = !tag.isBlank();
        if (hasTags) {
            return String.format("[%s] %s #%s", getStatusIcon(), getDescription(), tag);
        }
        return String.format("[%s] %s", getStatusIcon(), getDescription());
    }
}
