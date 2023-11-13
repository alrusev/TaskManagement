package exceptions;

public class NoSuchElementFoundException extends RuntimeException{
    public NoSuchElementFoundException() {
    }

    public NoSuchElementFoundException(String message) {
        super(message);
    }
}
