package ru.morou.api;

import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Класс отвечает за "описание" файла
 */

public class FileMessage extends AbstractMessage {

    private String filename;
    private byte[] data;
    private int partsCount;
    private int partNumber;

    public FileMessage(String filename, byte[] data, int partsCount, int partNumber) throws Exception {
        this.filename = filename;
        this.data = data;
        this.partsCount = partsCount;
        this.partNumber = partNumber;
    }

    public String getFileame() {
        return filename;
    }

    public byte[] getData() {
        return data;
    }

    public int getPartsCount() {
        return partsCount;
    }

    public int getPartNumber() {
        return partNumber;
    }

    public FileMessage(Path path) throws Exception{
        filename = path.getFileName().toString();
        data = Files.readAllBytes(path);
    }
}
