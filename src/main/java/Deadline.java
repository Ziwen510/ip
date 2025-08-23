public class Deadline extends Task {
    private String by;
    
    public Deadline(String description, String by) {
        super(description);
        this.by = by;
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
