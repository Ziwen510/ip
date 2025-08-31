package dukii;

import dukii.command.MarkCommand;
import dukii.command.UnmarkCommand;
import dukii.command.DeleteCommand;
import dukii.exception.DukiiException;
import dukii.storage.Storage;
import dukii.task.TaskList;
import dukii.task.ToDo;
import dukii.ui.Ui;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class CommandMarkUnmarkDeleteTest {
    private static class NoopStorage extends Storage {
        public NoopStorage() { super("./build/tmp/dukii-test.txt"); }
        @Override public void save(java.util.List tasks) throws IOException { /* no-op */ }
    }

    @Test
    void markUnmarkDelete_updatesState() throws Exception {
        ArrayList tasks = new ArrayList();
        TaskList list = new TaskList(tasks);
        Ui ui = new Ui();
        Storage storage = new NoopStorage();
        list.asList().add(new ToDo("buy milk"));

        new MarkCommand(1).execute(list, ui, storage);
        assertTrue(list.asList().get(0).isDone());

        new UnmarkCommand(1).execute(list, ui, storage);
        assertFalse(list.asList().get(0).isDone());

        int before = list.asList().size();
        new DeleteCommand(1).execute(list, ui, storage);
        assertEquals(before - 1, list.asList().size());
    }

    @Test
    void mark_outOfRange_rejected() {
        ArrayList tasks = new ArrayList();
        TaskList list = new TaskList(tasks);
        Ui ui = new Ui();
        Storage storage = new NoopStorage();
        DukiiException ex = assertThrows(DukiiException.class, () -> new MarkCommand(1).execute(list, ui, storage));
        assertTrue(ex.getMessage().contains("no tasks to mark"));
    }
}


