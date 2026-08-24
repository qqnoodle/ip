import java.time.LocalDateTime;

public class DeadlineCommand extends Command{
    private final String description;
    private final LocalDateTime dueBy;

    public DeadlineCommand(String description, LocalDateTime dueBy) {
        this.description = description;
        this.dueBy = dueBy;
    }

    @Override
    public void execute(Ui ui, TaskList taskList, Storage storage) {
        taskList.insert(new Deadline(description, dueBy));
        storage.save(taskList);
        System.out.println("Inscribing request: \n"
                + "   " + taskList.getTaskByNumber(taskList.getSize()).toString() + "\n"
                + taskList.getSize() + " tasks are being tracked");
    }
}
