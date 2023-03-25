package Exceptions.sql;

public class SqlSaveException extends RuntimeException{
    public SqlSaveException(String message) {
        super(message);
    }
}
