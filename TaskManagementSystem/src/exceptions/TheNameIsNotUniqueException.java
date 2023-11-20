package exceptions;

public class TheNameIsNotUniqueException extends RuntimeException{
    public TheNameIsNotUniqueException() {
    }

    public TheNameIsNotUniqueException(String message) {
        super(message);
    }
}
