package com.geekbrains.hibernate;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "readers")
public class Reader {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "name")
    private String name;

    @ManyToMany
    @JoinTable(
            name = "books_readers",
            joinColumns = @JoinColumn(name = "reader_id"),
            inverseJoinColumns = @JoinColumn(name = "book_id")
    )
    private List<Book> bookList;

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

    public List<Book> getBookList() {
        return bookList;
    }

    public void setBookList(List<Book> bookList) {
        this.bookList = bookList;
    }

    public Reader() {
    }

    @Override
    public String toString(){
        String allBooks = "";
        for (Book o: bookList) {
            allBooks += o.getTitle() + " ";
        }
        return "Reader [" + id + " " + name + " " + "[ " + allBooks +  "] ]";
    }
}
