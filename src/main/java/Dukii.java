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
            } else if (input.startsWith("todo ")) {
                String taskDescription = input.substring(5).trim();
                if (taskDescription.isEmpty()) {
                    System.out.println("What do you want to add?");
                } else {
                    tasks.add(new ToDo(taskDescription));
                    System.out.println("Got it. I've added this todo:");
                    System.out.println("  " + tasks.get(tasks.size() - 1));
                    System.out.println("Now you have " + tasks.size() + " task(s) in the list.");
                }
            } else if (input.startsWith("event ")) {
                String[] parts = input.substring(6).trim().split(" from ");
                if (parts.length != 2 || parts[0].isEmpty() || parts[1].isEmpty()) {
                    System.out.println("Oopsie! Please use: event <description> from <start_time> to <end_time>");
                } else {
                    String[] timeParts = parts[1].trim().split(" to ");
                    if (timeParts.length != 2 || timeParts[0].isEmpty() || timeParts[1].isEmpty()) {
                        System.out.println("Oopsie! Please use: event <description> from <start_time> to <end_time>");
                    } else {
                        tasks.add(new Event(parts[0].trim(), timeParts[0].trim(), timeParts[1].trim()));
                        System.out.println("Got it. I've added this event:");
                        System.out.println("  " + tasks.get(tasks.size() - 1));
                        System.out.println("Now you have " + tasks.size() + " task(s) in the list.");
                    }
                }
            } else if (input.startsWith("deadline ")) {
                String[] parts = input.substring(9).trim().split(" by ");
                if (parts.length != 2 || parts[0].isEmpty() || parts[1].isEmpty()) {
                    System.out.println("Oopsie! Please use: deadline <description> by <time>");
                } else {
                    tasks.add(new Deadline(parts[0].trim(), parts[1].trim()));
                    System.out.println("Got it. I've added this task:");
                    System.out.println("  " + tasks.get(tasks.size() - 1));
                    System.out.println("Now you have " + tasks.size() + " task(s) in the list.");
                }
            } else if (input.startsWith("mark ")) {
                try {
                    int taskIndex = Integer.parseInt(input.substring(5).trim()) - 1;
                    if (taskIndex >= 0 && taskIndex < tasks.size()) {
                        Task task = tasks.get(taskIndex);
                        if (task.isDone()) {
                            System.out.println("This task is already marked as done~");
                        } else {
                            task.markAsDone();
                            System.out.println("Good job sweety! I've marked this task as done:");
                            System.out.println("  " + task);
                        }
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
                        Task task = tasks.get(taskIndex);
                        if (!task.isDone()) {
                            System.out.println("This task is not marked as done yet~");
                        } else {
                            task.markAsPending();
                            System.out.println("OK, I've marked this task as not done yet:");
                            System.out.println("  " + task);
                        }
                    } else {
                        System.out.println("What task do you want to unmark?");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("What task do you want to unmark?");
                }
            } else {
                System.out.println("What can I do for you?");
            }
        }
        scanner.close();
    }
}
