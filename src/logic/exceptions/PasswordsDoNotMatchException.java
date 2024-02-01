package logic.exceptions;

/**
 * PasswordsDoNotMatchException is a custom exception class that extends
 * Exception. It is used to indicate a situation where two password fields do
 * not match. This class provides constructors to create exceptions with
 * optional custom messages.
 */
public class PasswordsDoNotMatchException extends Exception {

    private String message;

    /**
     * Constructs a new, default PasswordsDoNotMatchException. This constructor
     * creates a PasswordsDoNotMatchException without a detail message.
     */
    public PasswordsDoNotMatchException() {
    }

    /**
     * Constructs a PasswordsDoNotMatchException with a specified detail
     * message. This allows for a custom message to be provided, explaining the
     * specific reason for the exception.
     *
     * @param message the detail message that specifies the reason for the
     * exception.
     */
    public PasswordsDoNotMatchException(String message) {
        this.message = message;
    }

    /**
     * Retrieves the detail message associated with this exception. This method
     * returns the custom message that was provided during the exception's
     * construction.
     *
     * @return the detail message of this exception, or null if no message was
     * set.
     */
    @Override
    public String getMessage() {
        return this.message;
    }
}
