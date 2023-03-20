package Exceptions.sql;

public class SqlUpdateException extends RuntimeException {
    public SqlUpdateException(String message) {
        super(message);
    }
}
