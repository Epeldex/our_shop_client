package ui.exceptions;

public class SignUpException extends Exception {

    public String message;

    public SignUpException() {}

    public SignUpException(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }



}
