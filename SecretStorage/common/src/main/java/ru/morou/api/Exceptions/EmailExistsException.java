package ru.morou.api.Exceptions;

public class EmailExistsException extends Exception {
    private String existingEmail;

    public EmailExistsException(String existingEmail) {
        super("Email " + existingEmail + " already exists");
        this.existingEmail = existingEmail;
    }

    public String getExistingEmail() {
        return existingEmail;
    }
}
