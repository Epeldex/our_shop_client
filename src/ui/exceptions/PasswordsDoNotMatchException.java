package ui.exceptions;

/**
 * This exception is thrown when a password is considered too short based on a
 * defined policy. It is used to enforce a minimum password length requirement.
 */
public class PasswordsDoNotMatchException extends Exception {

    public String getMessage() {
        return "The passwords do not match";
    }

}
