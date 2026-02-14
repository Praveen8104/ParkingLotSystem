package exceptions;

public class RateMismatchException extends RuntimeException {
    public RateMismatchException(String message) {
        super(message);
    }
}
