package exceptions;

/**
 * This exception is thrown when a data format does not match the expected
 * format. It indicates that a value or input does not conform to the specified
 * format.
 */
public class IncorrectFormatException extends Exception {
    private String message;

    public IncorrectFormatException() {}
    
    public IncorrectFormatException(String message) {
        this.message = message;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
