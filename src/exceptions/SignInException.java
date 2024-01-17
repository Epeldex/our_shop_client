package exceptions;

public class SignInException extends Exception {
    private String message;

    public SignInException() {}

    public SignInException(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
