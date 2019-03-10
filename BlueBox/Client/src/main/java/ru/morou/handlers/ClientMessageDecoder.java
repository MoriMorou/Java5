package ru.morou.handlers;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;


import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import ru.morou.JavaFX.Controller;
import ru.morou.processors.FileTransferHelper;
import ru.morou.queries.StandardJsonQuery;
import ru.morou.queries.json.JsonSendFile;

/**
 * Декодер клиента: распарсивает json, записывает файлы, формирует объекты
 * TransferMessage
 * 
 * @author morou
 */
public class ClientMessageDecoder extends LengthFieldBasedFrameDecoder {
	
	public ClientMessageDecoder() {
        super(FileTransferHelper.BUFFER_LEN + 52, 0, 4, 0, 4);
    }

	private MessageDecodeHelper decoder = new MessageDecodeHelper();
	
	@Override
	protected Object decode(ChannelHandlerContext ctx, ByteBuf in) throws Exception {

		ByteBuf frame = (ByteBuf) super.decode(ctx, in);
        if (frame == null) {
            return null;
        }
        
        in.release();
        
        StandardJsonQuery json = decoder.decodeTransference(frame, MessageDecodeHelper.CLIENT_COM_SENDFILE);

        if (json instanceof JsonSendFile) {
        	JsonSendFile jsonFile = (JsonSendFile)json;
        	
        	if (jsonFile.getPartsAmount() > 0) {
        	
	        	String path = Controller.getFilePath(jsonFile.getFilePath()) +
	        			      File.separator +
	        			      jsonFile.getFileName();
	        	
	        	jsonFile.setFilePath(path);
	        	Files.deleteIfExists(Paths.get(path));
	        	
	        	json = null;
        	}
        }
        return json;
	}
}
