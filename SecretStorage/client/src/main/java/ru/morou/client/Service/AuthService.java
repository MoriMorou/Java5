package ru.morou.client.Service;

public interface AuthService {

    /**
     * Интерфейс для авторизации.
     * @param login user login
     * @param password user password
     * @return true if authentication succeeded
     */
    boolean isAuthorized(final String login, final String password);

}
