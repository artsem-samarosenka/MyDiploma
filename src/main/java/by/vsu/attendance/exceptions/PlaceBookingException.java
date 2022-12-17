package by.vsu.attendance.exceptions;

public class PlaceBookingException extends RuntimeException {
    public PlaceBookingException() {
        super();
    }

    public PlaceBookingException(String message) {
        super(message);
    }

    public PlaceBookingException(String message, Throwable cause) {
        super(message, cause);
    }
}
