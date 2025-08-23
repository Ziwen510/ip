import java.util.Scanner;
import java.util.ArrayList;

public class Dukii {
    private static ArrayList<Task> tasks = new ArrayList<>();
    
    public static void main(String[] args) {
        System.out.println("Hello sweety~ I'm Dukii!");
        System.out.println("A new day starts! What can I do for you today?");

        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNextLine()) {
            String input = scanner.nextLine().trim();
            if (input.equals("bye")) {
                System.out.println("Bye~ Have a good day!");
                break;
            } else if (input.equals("list")) {
                if (tasks.isEmpty()) {
                    System.out.println("No task there! Enjoy your day sweety~");
                } else {
                    System.out.println("Here are the tasks in your list:");
                    for (int i = 0; i < tasks.size(); i++) {
                        System.out.println((i + 1) + "." + tasks.get(i));
                    }
                }
            } else if (input.startsWith("add:")) {
                String taskDescription = input.substring(4).trim();
                if (taskDescription.isEmpty()) {
                    System.out.println("What do you want to add?");
                } else {
                    tasks.add(new Task(taskDescription));
                    System.out.println("Got it. I've added this task:");
                    System.out.println("  " + tasks.get(tasks.size() - 1));
                    System.out.println("Now you have " + tasks.size() + " task(s) in the list.");
                }
            } else if (input.startsWith("mark ")) {
                try {
                    int taskIndex = Integer.parseInt(input.substring(5).trim()) - 1;
                    if (taskIndex >= 0 && taskIndex < tasks.size()) {
                        tasks.get(taskIndex).markAsDone();
                        System.out.println("Good job sweety! I've marked this task as done:");
                        System.out.println("  " + tasks.get(taskIndex));
                    } else {
                        System.out.println("What task do you want to mark as done?");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("What task do you want to mark as done?");
                }
            } else if (input.startsWith("unmark ")) {
                try {
                    int taskIndex = Integer.parseInt(input.substring(7).trim()) - 1;
                    if (taskIndex >= 0 && taskIndex < tasks.size()) {
                        tasks.get(taskIndex).markAsPending();
                        System.out.println("OK, I've marked this task as not done yet:");
                        System.out.println("  " + tasks.get(taskIndex));
                    } else {
                        System.out.println("What task do you want to unmark?");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("What task do you want to unmark?");
                }
            } else {
                if (input.isEmpty()) {
                    System.out.println("What can I do for you?");
                } else {
                    tasks.add(new Task(input));
                    System.out.println("Got it. I've added this task:");
                    System.out.println("  " + tasks.get(tasks.size() - 1));
                    System.out.println("Now you have " + tasks.size() + " task(s) in the list.");
                }
            }
        }
        scanner.close();
    }
}

class Task {
    private String description;
    private boolean isDone;
    
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
    
    @Override
    public String toString() {
        return "[" + (isDone ? "X" : " ") + "] " + description;
    }
}
