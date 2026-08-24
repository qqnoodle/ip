public class MarkCommand extends Command{
    private final int taskNumber;

    public MarkCommand(int taskNumber) {
       this.taskNumber = taskNumber;
    }

    @Override
    public void execute(Ui ui, TaskList taskList, Storage storage) {
        Task task = taskList.getTaskByNumber(taskNumber);
        task.markAsDone();
        storage.save(taskList);
        ui.showMessage("A worthy task! Arrodes has marked it as done:");
        ui.showMessage("  " + task);
    }
}
