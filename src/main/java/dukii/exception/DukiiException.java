package dukii.exception;

/**
 * Custom exception class for Dukii application-specific errors.
 * 
 * <p>This exception is thrown when errors occur during command execution,
 * parsing, or other application operations. It provides a way to distinguish
 * between application-specific errors and system errors, allowing for better
 * error handling and user feedback.</p>
 * 
 * <p>The exception message should be user-friendly and provide clear guidance
 * on how to resolve the issue.</p>
 * 
 * @author Wang Ziwen & AIs
 * @version 1.0
 */
public class DukiiException extends Exception {
<<<<<<< HEAD
    /**
     * Constructs a new DukiiException with the specified detail message.
     * 
     * <p>The message should be written in a user-friendly manner, explaining
     * what went wrong and potentially how to fix it.</p>
     * 
     * @param message the detail message explaining the error
     */
=======
    
>>>>>>> branch-A-CodingStandard
    public DukiiException(String message) {
        super(message);
    }
}
