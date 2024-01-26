package logic.exceptions;

public class PasswordsDoNotMatchException extends Exception {
    private String message;

    public PasswordsDoNotMatchException() {}

    public PasswordsDoNotMatchException(String message) {
        this.message = message;
    }

    public String getMessage() {
        return this.message;
    }
}
