package logic.exceptions;

public class PasswordsDoNotMatchException {
    private String message;

    public PasswordsDoNotMatchException() {}

    public PasswordsDoNotMatchException(String message) {
        this.message = message;
    }

    public String getMessage() {
        return this.message;
    }
}
