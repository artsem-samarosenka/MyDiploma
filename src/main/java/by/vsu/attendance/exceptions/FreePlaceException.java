package by.vsu.attendance.exceptions;

public class FreePlaceException extends RuntimeException {

    public FreePlaceException() {
        super();
    }

    public FreePlaceException(String message) {
        super(message);
    }

    public FreePlaceException(String message, Throwable cause) {
        super(message, cause);
    }
}
