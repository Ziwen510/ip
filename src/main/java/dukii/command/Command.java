package dukii.command;

import dukii.task.TaskList;
import dukii.ui.Ui;
import dukii.storage.Storage;
import dukii.exception.DukiiException;

public abstract class Command {
    public abstract void execute(TaskList tasks, Ui ui, Storage storage) throws DukiiException;
    public boolean isExit() { return false; }
    public boolean modifiesStorage() { return true; }
}
