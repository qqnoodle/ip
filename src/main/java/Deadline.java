public class Deadline extends Task{

    private final String dueBy;

    public Deadline (String description, String dueBy) {
        super(description);
        this.dueBy = dueBy;
    }

    @Override
    public String toString() {
        return String.format("[D]%s (by: %s)", super.toString(), dueBy);
    }
}
