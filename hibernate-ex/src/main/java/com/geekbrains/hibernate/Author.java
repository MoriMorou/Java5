package com.geekbrains.hibernate;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.GeneratorType;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "authors")
public class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "author_id")
    @Cascade(org.hibernate.annotations.CascadeType.DELETE)
    private List<Book> books;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Author() {
    }

    @Override
    public String toString(){
        String allBooks = "";
        for (Book o: books) {
            allBooks += o.getTitle() + " ";
        }
        return "Author [" + id + " " + name + " " + "[ " + allBooks +  "] ]";
    }
}
