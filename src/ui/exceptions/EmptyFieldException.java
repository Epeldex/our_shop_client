package ui.exceptions;

/**
 * This exception is thrown when a field is expected to have a value but is
 * found empty or null. It indicates that a required field is missing or has not
 * been provided by the user.
 */
public class EmptyFieldException extends Exception {

    private String message;

    public EmptyFieldException() {}

    public EmptyFieldException(String message) {
        this.message = message;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    } 
}
