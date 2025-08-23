import java.util.Scanner;
import java.util.ArrayList;

public class Dukii {
    private static ArrayList<Task> tasks = new ArrayList<>();
    
    public static void main(String[] args) {
        System.out.println("Hello sweety~ I'm Dukii!");
        System.out.println("A new day starts! What can I do for you today?");

        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNextLine()) {
            try {
                String input = scanner.nextLine().trim();
                if (input.equals("bye")) {
                    System.out.println("Bye~ Have a good day!");
                    break;
                } else if (input.equals("list")) {
                    handleListCommand();
                } else if (input.startsWith("todo ")) {
                    handleTodoCommand(input);
                } else if (input.startsWith("event ")) {
                    handleEventCommand(input);
                } else if (input.startsWith("deadline ")) {
                    handleDeadlineCommand(input);
                } else if (input.startsWith("mark ")) {
                    handleMarkCommand(input);
                } else if (input.startsWith("unmark ")) {
                    handleUnmarkCommand(input);
                } else if (input.startsWith("delete ")) {
                    handleDeleteCommand(input);
                } else if (input.isEmpty()) {
                    System.out.println("What can I do for you?");
                } else {
                    handleUnknownCommand(input);
                }
            } catch (DukiiException e) {
                System.out.println("Oopsie! " + e.getMessage());
            } catch (Exception e) {
                System.out.println("Oh no my sweety, something unexpected happened. Please try again.");
            }
        }
        scanner.close();
    }
    
    private static void handleListCommand() {
        if (tasks.isEmpty()) {
            System.out.println("No task there! Enjoy your day sweety~");
        } else {
            System.out.println("Here are the tasks in your list:");
            for (int i = 0; i < tasks.size(); i++) {
                System.out.println((i + 1) + "." + tasks.get(i));
            }
        }
    }
    
    private static void handleTodoCommand(String input) throws DukiiException {
        String taskDescription = input.substring(5).trim();
        if (taskDescription.isEmpty()) {
            throw new DukiiException("What would you like me to add to your list, sweety?");
        }
        tasks.add(new ToDo(taskDescription));
        System.out.println("Got it. I've added this todo:");
        System.out.println("  " + tasks.get(tasks.size() - 1));
        System.out.println("Now you have " + tasks.size() + " task(s) in the list.");
    }
    
    private static void handleEventCommand(String input) throws DukiiException {
        if (!input.contains(" from ")) {
            throw new DukiiException("Sweetie, for events I need to know when they start and end! Try: event <description> from <start_time> to <end_time>");
        }
        String[] parts = input.substring(6).trim().split(" from ");
        if (parts.length != 2 || parts[0].isEmpty() || parts[1].isEmpty()) {
            throw new DukiiException("Hmm, I'm a bit confused! Please tell me: event <description> from <start_time> to <end_time>");
        }
        if (!parts[1].contains(" to ")) {
            throw new DukiiException("I need both start and end times, honey! Use: event <description> from <start_time> to <end_time>");
        }
        String[] timeParts = parts[1].trim().split(" to ");
        if (timeParts.length != 2 || timeParts[0].isEmpty() || timeParts[1].isEmpty()) {
            throw new DukiiException("Oops! I need the complete event details: event <description> from <start_time> to <end_time>");
        }
        tasks.add(new Event(parts[0].trim(), timeParts[0].trim(), timeParts[1].trim()));
        System.out.println("Got it. I've added this event:");
        System.out.println("  " + tasks.get(tasks.size() - 1));
        System.out.println("Now you have " + tasks.size() + " task(s) in the list.");
    }
    
    private static void handleDeadlineCommand(String input) throws DukiiException {
        if (!input.contains(" by ")) {
            throw new DukiiException("Honey, I need to know when this is due! Please use: deadline <description> by <time>");
        }
        String[] parts = input.substring(9).trim().split(" by ");
        if (parts.length != 2 || parts[0].isEmpty() || parts[1].isEmpty()) {
            throw new DukiiException("Sweetie, I need both the task and when it's due! Try: deadline <description> by <time>");
        }
        tasks.add(new Deadline(parts[0].trim(), parts[1].trim()));
        System.out.println("Got it. I've added this task:");
        System.out.println("  " + tasks.get(tasks.size() - 1));
        System.out.println("Now you have " + tasks.size() + " task(s) in the list.");
    }
    
    private static void handleMarkCommand(String input) throws DukiiException {
        if (input.length() <= 5) {
            throw new DukiiException("Honey, which task would you like me to mark as done? Please provide a number!");
        }
        try {
            int taskIndex = Integer.parseInt(input.substring(5).trim()) - 1;
            if (taskIndex < 0 || taskIndex >= tasks.size()) {
                throw new DukiiException("Oh dear! That task number doesn't exist. Please choose between 1 and " + tasks.size() + ".");
            }
            Task task = tasks.get(taskIndex);
            if (task.isDone()) {
                System.out.println("This task is already marked as done~");
            } else {
                task.markAsDone();
                System.out.println("Good job sweety! I've marked this task as done:");
                System.out.println("  " + task);
            }
        } catch (NumberFormatException e) {
            throw new DukiiException("Sweetie, I need a real number to mark the task! Please try again.");
        }
    }
    
    private static void handleUnmarkCommand(String input) throws DukiiException {
        if (input.length() <= 7) {
            throw new DukiiException("Honey, which task should I unmark? Please give me a number!");
        }
        try {
            int taskIndex = Integer.parseInt(input.substring(7).trim()) - 1;
            if (taskIndex < 0 || taskIndex >= tasks.size()) {
                throw new DukiiException("Oops! That task number is out of range. Please pick between 1 and " + tasks.size() + ".");
            }
            Task task = tasks.get(taskIndex);
            if (!task.isDone()) {
                System.out.println("This task is not marked as done yet~");
            } else {
                task.markAsPending();
                System.out.println("OK, I've marked this task as not done yet:");
                System.out.println("  " + task);
            }
        } catch (NumberFormatException e) {
            throw new DukiiException("Honey, I need a proper number to unmark the task! Please try again.");
        }
    }
    
    private static void handleDeleteCommand(String input) throws DukiiException {
        if (tasks.isEmpty()) {
            throw new DukiiException("Oh sweety, there are no tasks to delete! Your list is empty.");
        }
        if (input.length() <= 7) {
            throw new DukiiException("Sweetie, which task would you like me to remove? Please provide a number!");
        }
        try {
            int taskIndex = Integer.parseInt(input.substring(7).trim()) - 1;
            if (taskIndex < 0 || taskIndex >= tasks.size()) {
                throw new DukiiException("Oh no! That task number doesn't exist. Please choose between 1 and " + tasks.size() + ".");
            }
            Task deletedTask = tasks.get(taskIndex);
            tasks.remove(taskIndex);
            System.out.println("Noted. I've removed this task:");
            System.out.println("  " + deletedTask);
            System.out.println("Now you have " + tasks.size() + " task(s) in the list.");
        } catch (NumberFormatException e) {
            throw new DukiiException("Honey, I need a real number to delete the task! Please try again.");
        }
    }
    
    private static void handleUnknownCommand(String input) throws DukiiException {
        throw new DukiiException("Oh honey, I'm not sure what you mean by that! Could you try one of my commands?");
    }
}
