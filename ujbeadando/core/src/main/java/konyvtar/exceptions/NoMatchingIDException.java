package konyvtar.exceptions;

public class NoMatchingIDException extends Exception{
    public NoMatchingIDException() {
    }

    public NoMatchingIDException(String message) {
        super(message);
    }
}
