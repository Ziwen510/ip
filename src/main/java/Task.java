public abstract class Task {
    protected String description;
    protected boolean isDone;
    
    public Task(String description) {
        this.description = description;
        this.isDone = false;
    }
    
    public void markAsDone() {
        this.isDone = true;
    }
    
    public void markAsPending() {
        this.isDone = false;
    }
    
    public boolean isDone() {
        return this.isDone;
    }
    
    public abstract String getTaskType();
    
    @Override
    public String toString() {
        return "[" + getTaskType() + "][" + (isDone ? "X" : " ") + "] " + description;
    }
}
