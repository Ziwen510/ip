package dukii.storage;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.time.LocalDate;
import dukii.task.*;

public class Storage {
    
    private static final String TASK_SEPARATOR = " | ";
    private static final String TASK_TYPE_TODO = "T";
    private static final String TASK_TYPE_DEADLINE = "D";
    private static final String TASK_TYPE_EVENT = "E";
    private static final String TASK_STATUS_DONE = "1";
    private static final String TASK_STATUS_PENDING = "0";
    
    private final String filePath;

    public Storage(String filePath) {
        this.filePath = filePath;
    }

    public ArrayList<Task> load() throws IOException {
        Path path = Path.of(filePath);
        File file = path.toFile();
        
        if (!file.exists()) {
            createFileAndDirectories(file);
            return new ArrayList<>();
        }
        
        List<String> lines = Files.readAllLines(path);
        ArrayList<Task> tasks = new ArrayList<>();
        
        for (String line : lines) {
            if (line == null || line.trim().isEmpty()) {
                continue;
            }
            
            Task task = parseTask(line);
            if (task != null) {
                tasks.add(task);
            }
        }
        
        return tasks;
    }

    public void save(List<Task> tasks) throws IOException {
        Path path = Path.of(filePath);
        File file = path.toFile();
        File parent = file.getParentFile();
        
        if (parent != null && !parent.exists()) {
            parent.mkdirs();
        }
        
        try (FileWriter writer = new FileWriter(file)) {
            for (Task task : tasks) {
                writer.write(serializeTask(task));
                writer.write(System.lineSeparator());
            }
        }
    }

    private void createFileAndDirectories(File file) throws IOException {
        File parent = file.getParentFile();
        if (parent != null && !parent.exists()) {
            parent.mkdirs();
        }
        file.createNewFile();
    }

    private String serializeTask(Task task) {
        String status = task.isDone() ? TASK_STATUS_DONE : TASK_STATUS_PENDING;
        
        if (task instanceof ToDo) {
            return String.join(TASK_SEPARATOR, TASK_TYPE_TODO, status, task.getDescription());
        } else if (task instanceof Deadline) {
            Deadline deadline = (Deadline) task;
            return String.join(TASK_SEPARATOR, TASK_TYPE_DEADLINE, status, 
                             task.getDescription(), deadline.getByDate().toString());
        } else if (task instanceof Event) {
            Event event = (Event) task;
            return String.join(TASK_SEPARATOR, TASK_TYPE_EVENT, status, 
                             task.getDescription(), event.getFromDate().toString(), 
                             event.getToDate().toString());
        }
        
        return "";
    }

    private Task parseTask(String line) {
        String[] parts = line.split("\\s*\\|\\s*");
        if (parts.length < 3) {
            return null;
        }
        
        String type = parts[0];
        boolean isDone = TASK_STATUS_DONE.equals(parts[1]);
        String description = parts[2];
        Task task = null;
        
        if (TASK_TYPE_TODO.equals(type)) {
            task = new ToDo(description);
        } else if (TASK_TYPE_DEADLINE.equals(type) && parts.length >= 4) {
            try {
                task = new Deadline(description, LocalDate.parse(parts[3]));
            } catch (Exception ignored) {
                return null;
            }
        } else if (TASK_TYPE_EVENT.equals(type) && parts.length >= 5) {
            try {
                task = new Event(description, LocalDate.parse(parts[3]), LocalDate.parse(parts[4]));
            } catch (Exception ignored) {
                return null;
            }
        }
        
        if (task != null && isDone) {
            task.markAsDone();
        }
        
        return task;
    }
}


