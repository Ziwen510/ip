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

/**
 * Storage class responsible for persisting and loading task data to/from the file system.
 * 
 * <p>This class handles the serialization and deserialization of tasks to ensure
 * data persistence across application sessions. It supports three task types:
 * todos, deadlines, and events, each with their own serialization format.</p>
 * 
 * <p>The storage format uses pipe-separated values with the following structure:</p>
 * <ul>
 *   <li>Todo: T | status | description</li>
 *   <li>Deadline: D | status | description | due_date</li>
 *   <li>Event: E | status | description | start_date | end_date</li>
 * </ul>
 * 
 * <p>Status is represented as "0" for pending and "1" for completed tasks.</p>
 * 
 * @author Wang Ziwen & AIs
 * @version 1.0
 */
public class Storage {
    
    private static final String TASK_SEPARATOR = " | ";
    private static final String TASK_TYPE_TODO = "T";
    private static final String TASK_TYPE_DEADLINE = "D";
    private static final String TASK_TYPE_EVENT = "E";
    private static final String TASK_STATUS_DONE = "1";
    private static final String TASK_STATUS_PENDING = "0";
    
    private final String filePath;

    /**
     * Constructs a new Storage instance with the specified file path.
     * 
     * @param filePath the path to the file where tasks will be stored
     */
    public Storage(String filePath) {
        this.filePath = filePath;
    }

    /**
     * Loads tasks from the storage file.
     * 
     * <p>This method reads the storage file and reconstructs Task objects from
     * the stored data. If the file doesn't exist, it creates a new empty file
     * and returns an empty task list. If the parent directory doesn't exist,
     * it creates the necessary directory structure.</p>
     * 
     * @return an ArrayList containing all loaded tasks
     * @throws IOException if an error occurs during file operations
     */
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

    /**
     * Saves the given task list to the storage file.
     * 
     * <p>This method serializes each task to its string representation and
     * writes it to the storage file. If the parent directory doesn't exist,
     * it creates the necessary directory structure. The file is overwritten
     * with the new task data.</p>
     * 
     * @param tasks the list of tasks to save
     * @throws IOException if an error occurs during file operations
     */
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
    
    /**
     * Serializes a task to its string representation for storage.
     * 
     * <p>This method converts a Task object to a pipe-separated string format
     * suitable for persistent storage. The format varies by task type and
     * includes the task type identifier, completion status, description,
     * and any relevant date information.</p>
     * 
     * @param task the task to serialize
     * @return a string representation of the task for storage
     */
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

    /**
     * Parses a stored task string and reconstructs the corresponding Task object.
     * 
     * <p>This method reads a line from the storage file and converts it back
     * to a Task object. It handles the different formats for each task type
     * and validates the data during parsing. If parsing fails for any reason,
     * the method returns null to skip that line.</p>
     * 
     * @param line the stored string representation of a task
     * @return the reconstructed Task object, or null if parsing fails
     */
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


