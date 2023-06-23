package by.vsu.attendance.exceptions;

public class StudentHasBookedPlace extends RuntimeException {

    public StudentHasBookedPlace() {
        super();
    }

    public StudentHasBookedPlace(String message) {
        super(message);
    }

    public StudentHasBookedPlace(String message, Throwable cause) {
        super(message, cause);
    }
}
