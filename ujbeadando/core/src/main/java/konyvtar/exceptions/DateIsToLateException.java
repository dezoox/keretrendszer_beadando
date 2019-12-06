package konyvtar.exceptions;

public class DateIsToLateException extends Exception {

    public DateIsToLateException() {
    }

    public DateIsToLateException(String message) {
        super(message);
    }
}
