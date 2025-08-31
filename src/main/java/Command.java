public abstract class Command {
    public abstract void execute(TaskList tasks, Ui ui, Storage storage) throws DukiiException;
    public boolean isExit() { return false; }
    public boolean modifiesStorage() { return true; }
}


