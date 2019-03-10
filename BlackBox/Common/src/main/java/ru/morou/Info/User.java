package ru.morou.Info;

import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.util.List;


@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;


    @Column(name = "firstname")
    private String firstname;


    @Column(name = "lastname")
    private String lastname;


    @Column(name = "login")
    private String login;


    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

//    @OneToMany(mappedBy = "users")
//    @Cascade(org.hibernate.annotations.CascadeType.DELETE)
//    private List<Files> files;

    public User(String firstname, String lastname, String login, String email, String password) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.login = login;
        this.email = email;
        this.password = password;
    }

    public User(){

    }

//    public User(String first_name, String last_name, String login, String email, List<Folder> folders) {
//        this.first_name = first_name;
//        this.last_name = last_name;
//        this.login = login;
//        this.email = email;
//        this.folders = folders;
//    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", first_name='" + firstname + '\'' +
                ", last_name='" + lastname + '\'' +
                ", login='" + login + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
