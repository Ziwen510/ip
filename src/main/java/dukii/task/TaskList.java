package dukii.task;

import java.util.ArrayList;

public class TaskList {
    private final ArrayList<Task> tasks;

    public TaskList(ArrayList<Task> tasks) {
        this.tasks = tasks;
    }

    public ArrayList<Task> asList() {
        return tasks;
    }
    
    public int getSize() {
        return tasks.size();
    }
    
    public boolean isEmpty() {
        return tasks.isEmpty();
    }
    
    public void addTask(Task task) {
        tasks.add(task);
    }
    
    public Task getTask(int index) {
        if (index < 0 || index >= tasks.size()) {
            return null;
        }
        return tasks.get(index);
    }
    
    public void removeTask(int index) {
        if (index >= 0 && index < tasks.size()) {
            tasks.remove(index);
        }
    }
}
