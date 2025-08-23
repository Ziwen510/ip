import java.util.Scanner;

public class Dukii {
    public static void main(String[] args) {
        System.out.println("Hello sweety~ I'm Dukii!");
        System.out.println("A new day starts! What can I do for you today?");

        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNextLine()) {
            String input = scanner.nextLine().trim();
            if (input.equals("bye")) {
                System.out.println("Bye~ Have a good day!");
                break;
            } else {
                System.out.println(input + "!!");
            }
        }
        scanner.close();
    }
}
