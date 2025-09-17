package dukii.ui;

/**
 * User Interface class responsible for displaying messages and interacting with the user.
 * 
 * <p>This class provides a simple text-based interface for the Dukii application.
 * It handles all user-facing output including welcome messages, task confirmations,
 * and error messages. The current implementation uses System.out.println for output,
 * but this could be extended in the future to support different output formats.</p>
 * 
 * <p>The UI is designed to be user-friendly with warm, encouraging language that
 * matches the application's personality.</p>
 * 
 * @author Wang Ziwen & AIs
 * @version 1.0
 */
public class Ui {
    private final StringBuilder buffer = new StringBuilder();
    /**
     * Displays a message to the user.
     * 
     * <p>This method outputs the given message to the console. It serves as
     * the primary method for communicating information to the user throughout
     * the application.</p>
     * 
     * @param message the message to display to the user
     */
    public void showMessage(final String message) {
        System.out.println(message);
        buffer.append(message).append(System.lineSeparator());
    }

    /**
     * Displays the welcome message when the application starts.
     * 
     * <p>This method shows a friendly greeting to welcome users to the Dukii
     * application and prompts them to start using the system.</p>
     */
    public void showWelcome() {
        String m1 = "Hello sweety~ I'm Dukii!";
        String m2 = "A new day starts! What can I do for you today?";
        showMessage(m1);
        showMessage(m2);
    }

    public String drainMessages() {
        String out = buffer.toString();
        buffer.setLength(0);
        return out.isEmpty() ? "" : out.trim();
    }
}


