public class Deadline extends Task {
    private String by;
    
    public Deadline(String description, String by) {
        super(description);
        this.by = by;
    }
    
    public String getBy() {
        return this.by;
    }

    @Override
    public String getTaskType() {
        return "D";
    }
    
    @Override
    public String toString() {
        return "[" + getTaskType() + "][" + (isDone ? "X" : " ") + "] " + description + " (by: " + by + ")";
    }
}
