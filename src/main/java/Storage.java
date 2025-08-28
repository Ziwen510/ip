import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class Storage {
    private final String filePath;

    public Storage(String filePath) {
        this.filePath = filePath;
    }

    public ArrayList<Task> load() throws IOException {
        Path path = Path.of(filePath);
        File file = path.toFile();
        if (!file.exists()) {
            File parent = file.getParentFile();
            if (parent != null && !parent.exists()) {
                parent.mkdirs();
            }
            file.createNewFile();
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

    private String serializeTask(Task task) {
        String status = task.isDone() ? "1" : "0";
        if (task instanceof ToDo) {
            return String.join(" | ", "T", status, task.getDescription());
        } else if (task instanceof Deadline) {
            Deadline d = (Deadline) task;
            return String.join(" | ", "D", status, task.getDescription(), d.getBy());
        } else if (task instanceof Event) {
            Event e = (Event) task;
            return String.join(" | ", "E", status, task.getDescription(), e.getFrom(), e.getTo());
        }
        return "";
    }

    private Task parseTask(String line) {
        String[] parts = line.split("\\s*\\|\\s*");
        if (parts.length < 3) {
            return null;
        }
        String type = parts[0];
        boolean isDone = "1".equals(parts[1]);
        String description = parts[2];
        Task task = null;
        if ("T".equals(type)) {
            task = new ToDo(description);
        } else if ("D".equals(type) && parts.length >= 4) {
            task = new Deadline(description, parts[3]);
        } else if ("E".equals(type) && parts.length >= 5) {
            task = new Event(description, parts[3], parts[4]);
        }
        if (task != null && isDone) {
            task.markAsDone();
        }
        return task;
    }
}


