package ru.morou.api;


public class CodeRequest extends AbstractMessage {
    private String codeRequest;

    public String codeRequest() {
        return codeRequest;
    }

    public CodeRequest(String codeRequest) {
        this.codeRequest = codeRequest;
    }
}
