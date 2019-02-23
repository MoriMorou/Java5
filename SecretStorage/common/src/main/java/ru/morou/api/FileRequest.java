package ru.morou.api;

import com.sun.xml.internal.ws.message.AbstractMessageImpl;

public class FileRequest extends AbstractMessage {
    private String filename;

    public String getFilename() {
        return filename;
    }

    public FileRequest(String filename) {
        this.filename = filename;
    }
}
