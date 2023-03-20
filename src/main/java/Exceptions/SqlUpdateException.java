package Exceptions;

public class SqlUpdateException extends RuntimeException {
    public SqlUpdateException(String message) {
        super(message);
    }
}
