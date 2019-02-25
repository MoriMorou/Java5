package ru.morou.api.Info;

import java.io.Serializable;

public class UserInfo implements Serializable {

    private final String firstname;
    private final String secondname;
    private final String login;
    private final String email;

    private UserInfo(String firstname, String lastname, String login, String email) {
        this.firstname = firstname;
        this.secondname = lastname;
        this.login = login;
        this.email = email;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getSecondname() {
        return secondname;
    }

    public String getLogin() {
        return login;
    }

    public String getEmail() {
        return email;
    }

    public static UserInfoBuilder builder() {
        return new UserInfoBuilder();
    }

    public static class UserInfoBuilder {

        private String firstName;
        private String secondname;
        private String login;
        private String email;

        public UserInfoBuilder firstname(String firstname) {
            this.firstName = firstname;
            return this;
        }

        public UserInfoBuilder secondname(String secondname) {
            this.secondname = secondname;
            return this;
        }

        public UserInfoBuilder login(String login) {
            this.login = login;
            return this;
        }

        public UserInfoBuilder email(String email) {
            this.email = email;
            return this;
        }

        public UserInfo build() {
            return new UserInfo(firstName, secondname, login, email);
        }

    }
}
