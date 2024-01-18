package exceptions;

public class LogicException extends Exception {

    private String message;

    public LogicException () {}

    public LogicException(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }


}
