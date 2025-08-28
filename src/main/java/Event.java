public class Event extends Task {
    private String from;
    private String to;
    
    public Event(String description, String from, String to) {
        super(description);
        this.from = from;
        this.to = to;
    }
    
    public String getFrom() {
        return this.from;
    }

    public String getTo() {
        return this.to;
    }

    @Override
    public String getTaskType() {
        return "E";
    }
    
    @Override
    public String toString() {
        return "[" + getTaskType() + "][" + (isDone ? "X" : " ") + "] " + description + " (from: " + from + " to: " + to + ")";
    }
}
