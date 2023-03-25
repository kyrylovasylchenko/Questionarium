package Exceptions.sql;

public class SqlGetByTopicException extends RuntimeException{
    public SqlGetByTopicException(String message) {
        super(message);
    }
}
