package logic.exceptions;

/**
 * LogicException is a custom exception class that extends Exception.
 * It is used to represent exceptions that occur due to logical errors
 * in the application's flow. This class provides constructors to create
 * exception instances with optional error messages and cause exceptions.
 */
public class LogicException extends Exception {

    /**
     * Constructs a new, default LogicException.
     * This constructor creates a LogicException without any detail message or cause.
     */
    public LogicException() {
    }

    /**
     * Constructs a LogicException with the specified detail message.
     * This constructor allows for providing additional information about the exception.
     *
     * @param msg the detail message that provides more information about the exception.
     */
    public LogicException(String msg) {
        super(msg);
    }

    /**
     * Constructs a LogicException with a detail message and a cause.
     * This constructor allows for chaining exceptions by providing an underlying cause.
     *
     * @param msg the detail message that provides more information about the exception.
     * @param e the cause of this exception. This is the exception that triggered this exception.
     */
    public LogicException(String msg, Exception e) {
        super(msg, e);
    }

    /**
     * Constructs a LogicException with a cause.
     * This constructor allows for chaining exceptions without providing a separate detail message.
     *
     * @param e the cause of this exception. This is the exception that triggered this exception.
     */
    public LogicException(Exception e) {
        super(e);
    }
}

