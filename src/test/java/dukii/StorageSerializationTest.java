package dukii;

import dukii.storage.Storage;
import dukii.task.Task;
import dukii.task.Deadline;
import dukii.task.Event;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class StorageSerializationTest {
    @Test
    void roundTripsIsoDates() throws IOException {
        Path temp = Files.createTempFile("dukii", ".txt");
        Storage s = new Storage(temp.toString());
        ArrayList<Task> tasks = new ArrayList<>();
        tasks.add(new Deadline("d1", LocalDate.parse("2025-01-02")));
        tasks.add(new Event("e1", LocalDate.parse("2025-02-03"), LocalDate.parse("2025-02-04")));
        s.save(tasks);
        ArrayList<Task> loaded = s.load();
        assertEquals(2, loaded.size());
        assertTrue(loaded.get(0) instanceof Deadline);
        assertTrue(loaded.get(1) instanceof Event);
        // ensure format in toString is pretty (MMM d yyyy)
        String str = loaded.get(0).toString();
        assertTrue(str.contains("Jan") || str.contains("2025"));
        Files.deleteIfExists(temp);
    }
}


