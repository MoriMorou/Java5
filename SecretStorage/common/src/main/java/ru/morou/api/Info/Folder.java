package ru.morou.api.Info;


import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "folders")
public class Folder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_folder")
    private int id_folder;

    @Column(name = "name")
    private String folder;


    @OneToMany(mappedBy = "folder")
    @Cascade(org.hibernate.annotations.CascadeType.DELETE)
    private List<File> files;

    public Folder(String folder, List<File> files) {
        this.folder = folder;
        this.files = files;
    }

    public int getId_folder() {
        return id_folder;
    }

    public void setId_folder(int id_folder) {
        this.id_folder = id_folder;
    }

    public String getFolder() {
        return folder;
    }

    public void setFolder(String folder) {
        this.folder = folder;
    }

    public List<File> getFiles() {
        return files;
    }

    public void setFiles(List<File> files) {
        this.files = files;
    }

    @Override
    public String toString() {
        return "Folder{" +
                "id_folder=" + id_folder +
                ", folder='" + folder + '\'' +
                ", files=" + files +
                '}';
    }
}
