import java.util.Scanner;
import java.util.ArrayList;

public class Dukii {
    private static ArrayList<String> tasks = new ArrayList<>();
    
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
                    tasks.add(taskDescription);
                    System.out.println("Got it. I've added this task:");
                    System.out.println("  " + taskDescription);
                    System.out.println("Now you have " + tasks.size() + " task(s) in the list.");
                }
            } else {
                if (input.isEmpty()) {
                    System.out.println("What can I do for you?");
                } else {
                    tasks.add(input);
                    System.out.println("Got it. I've added this task:");
                    System.out.println("  " + input);
                    System.out.println("Now you have " + tasks.size() + " task(s) in the list.");
                }
            }
        }
        scanner.close();
    }
}
