package ru.morou.api.Exceptions;

public class UserExistsException extends Exception {
    private String userExistException;

    public UserExistsException(String userExistException) {
        super("User: " + userExistException + "already exists");
        this.userExistException = userExistException;
    }

    public String getUserExistException() {
        return userExistException;
    }
}
