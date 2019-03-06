package ru.morou.api.Info;

import javax.persistence.*;

@Entity
@Table(name = "files")
public class File {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_file")
    private String id;

    @Column(name = "name_file")
    private String filename;

    @Column(name = "author")
    private String author;

    @Column(name = "data")
    private int data;

    @Column(name = "size")
    private int size;


    public File(String filename, String author, int data, int size) {
        this.filename = filename;
        this.author = author;
        this.data = data;
        this.size = size;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getData() {
        return data;
    }

    public void setData(int data) {
        this.data = data;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    @Override
    public String toString() {
        return "File{" +
                "id='" + id + '\'' +
                ", filename='" + filename + '\'' +
                ", author='" + author + '\'' +
                ", data=" + data +
                ", size=" + size +
                '}';
    }
}