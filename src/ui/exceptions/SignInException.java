package ui.exceptions;

/**
 * Custom exception for sign-in related errors.
 */
public class SignInException extends Exception {

    public SignInException(String message) {
        super(message);
    }
}
