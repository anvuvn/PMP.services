package cs545.property.exceptions;

public class ErrorException extends RuntimeException {
    String message;

    public ErrorException(String message) {
        super();
        this.message = message;
    }

    public String getMessage() {
        return this.message;
    }

}
