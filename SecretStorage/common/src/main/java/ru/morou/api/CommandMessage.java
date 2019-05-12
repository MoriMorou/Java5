package ru.morou.api;

/**
 * Класс отвечает за обработку комманд
 */
public class CommandMessage extends AbstractMessage {
    public static final int CMD_MSG_AUTH_OK = 23792837;
    public static final int CMD_MSG_REQUEST_FILES_LIST = 340274982;
    public static final int CMD_MSG_REQUEST_SERVER_DELETE_FILE = 23963746;
    public static final int CMD_MSG_REQUEST_FILE_DOWNLOAD = 398472948;

    private int type;
    private Object[] attachment;

    public CommandMessage(int type, Object... attachment) {
        this.type = type;
        this.attachment = attachment;
    }

    public int getType() {
        return type;
    }

    public Object[] getAttachment(){
        return attachment;
    }
}
