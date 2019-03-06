package ru.morou.api;


import ru.morou.api.Exceptions.EmailExistsException;
import ru.morou.api.Exceptions.UserExistsException;

import java.io.InputStream;
import java.util.List;

public interface MainService {

    void addNewUser(String username, String password, String firstName, String lastName, String email)
        throws UserExistsException, EmailExistsException;


}