package dukii.task;

public abstract class Task {
    
    protected String description;
    protected boolean isDone;
    
    public Task(String description) {
        this.description = description;
        this.isDone = false;
    }
    
    public String getDescription() {
        return description;
    }

    public void markAsDone() {
        isDone = true;
    }
    
    public void markAsPending() {
        isDone = false;
    }
    
    public boolean isDone() {
        return isDone;
    }
    
    public abstract String getTaskType();
    
    @Override
    public String toString() {
        return "[" + getTaskType() + "][" + (isDone ? "X" : " ") + "] " + description;
    }
}
