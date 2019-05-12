//package ru.morou.api.Info;
//
//import lombok.AllArgsConstructor;
//import lombok.Getter;
//import lombok.Setter;
//import org.hibernate.annotations.Cascade;
//
//import javax.persistence.*;
//
//import java.util.List;
//
//@Getter
//@Setter
//@AllArgsConstructor
//@Entity
//@Table(name = "users")
//public class User {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(name = "id_user")
//    private int id;
//
//
//    @Column(name = "firstname")
//    private String first_name;
//
//
//    @Column(name = "lastname")
//    private String last_name;
//
//
//    @Column(name = "nickname")
//    private String login;
//
//
//    @Column(name = "email")
//    private String email;
//
//
//    @OneToMany(mappedBy = "author")
//    @Cascade(org.hibernate.annotations.CascadeType.DELETE)
//    private List<Folder> folders;
//
//    public User(String first_name, String last_name, String login, String email, List<Folder> folders) {
//        this.first_name = first_name;
//        this.last_name = last_name;
//        this.login = login;
//        this.email = email;
//        this.folders = folders;
//    }
//
//    public User(){
//    }
//
//    public int getId() {
//        return id;
//    }
//
//    public void setId(int id) {
//        this.id = id;
//    }
//
//    public String getFirst_name() {
//        return first_name;
//    }
//
//    public void setFirst_name(String first_name) {
//        this.first_name = first_name;
//    }
//
//    public String getLast_name() {
//        return last_name;
//    }
//
//    public void setLast_name(String last_name) {
//        this.last_name = last_name;
//    }
//
//    public String getLogin() {
//        return login;
//    }
//
//    public void setLogin(String login) {
//        this.login = login;
//    }
//
//    public String getEmail() {
//        return email;
//    }
//
//    public void setEmail(String email) {
//        this.email = email;
//    }
//
//    public List<Folder> getFolders() {
//        return folders;
//    }
//
//    public void setFolders(List<Folder> folders) {
//        this.folders = folders;
//    }
//
//    @Override
//    public String toString() {
//        String allFolder = "";
//        for (Folder f : folders) {
//            allFolder += f.getFolder() + " ";
//        }
//        return "User{" +
//                "id=" + id +
//                ", first_name='" + first_name + '\'' +
//                ", last_name='" + last_name + '\'' +
//                ", login='" + login + '\'' +
//                ", email='" + email + '\'' +
//                ", folders=" + folders +
//                '}';
//    }
//}
//
