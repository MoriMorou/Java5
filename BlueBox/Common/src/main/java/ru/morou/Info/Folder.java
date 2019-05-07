package ru.morou.Info;

import javax.persistence.*;
import org.hibernate.annotations.Cascade;

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
    private List<FileBox> fileBoxes;

    public Folder(String folder, List<FileBox> fileBoxes) {
        this.folder = folder;
        this.fileBoxes = fileBoxes;
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

    public List<FileBox> getFileBoxes() {
        return fileBoxes;
    }

    public void setFileBoxes(List<FileBox> fileBoxes) {
        this.fileBoxes = fileBoxes;
    }

    @Override
    public String toString() {
        return "Folder{" +
                "id_folder=" + id_folder +
                ", folder='" + folder + '\'' +
                ", fileBoxes=" + fileBoxes +
                '}';
    }
}
