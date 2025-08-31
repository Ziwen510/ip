public class MarkCommand extends Command {
    private final int index;
    public MarkCommand(int index) { this.index = index; }
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws DukiiException {
        if (tasks.asList().isEmpty()) {
            throw new DukiiException("Oh sweety, there are no tasks to mark! Your list is empty.");
        }
        if (index < 1 || index > tasks.asList().size()) {
            throw new DukiiException("Oh dear! That task number doesn't exist. Please choose between 1 and " + tasks.asList().size() + ".");
        }
        Task task = tasks.asList().get(index - 1);
        if (task.isDone()) {
            ui.showMessage("This task is already marked as done~");
        } else {
            task.markAsDone();
            ui.showMessage("Good job sweety! I've marked this task as done:");
            System.out.println("  " + task);
        }
    }
}


