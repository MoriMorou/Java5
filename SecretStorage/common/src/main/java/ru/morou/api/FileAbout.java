package ru.morou.api;

import java.io.File;
import java.io.Serializable;

/**
 * Обертка
 */

// FIXME: 5/9/2019 проверить на вшивость еще раз
public class FileAbout implements Serializable {

    private File file;
    private String name;
    private long size;

    public FileAbout(File file) {
    }

    public File getFile() {
        return file;
    }

    public String getName() {
        return name;
    }

    public void getSize() {
        this.size = size;
    }

    public FileAbout(File file, String name, long size) {
        this.file = file;
        this.name = file.getName();
        this.size = file.length();
    }
}
