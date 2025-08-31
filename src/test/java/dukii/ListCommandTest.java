package dukii;

import dukii.command.ListCommand;
import dukii.storage.Storage;
import dukii.task.TaskList;
import dukii.ui.Ui;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class ListCommandTest {
    @Test
    void list_empty_showsMessage() throws Exception {
        ArrayList tasks = new ArrayList();
        TaskList list = new TaskList(tasks);
        Ui ui = new Ui();
        Storage storage = new Storage("./build/tmp/dukii-test.txt");

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(baos);
        PrintStream old = System.out;
        System.setOut(ps);
        try {
            new ListCommand().execute(list, ui, storage);
        } finally {
            System.setOut(old);
        }
        String out = baos.toString();
        assertTrue(out.contains("No task there!"));
        assertFalse(new ListCommand().modifiesStorage());
    }
}


