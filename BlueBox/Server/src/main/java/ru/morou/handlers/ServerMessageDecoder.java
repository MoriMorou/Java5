package ru.morou.handlers;

import java.nio.file.Path;


import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import ru.morou.AuthManager;
import ru.morou.processors.FileTransferHelper;

/**
 * Декодер сервера: распарсивает json, записывает файлы,
 * формирует объекты TransferMessage
 * @author morou
 */
public class ServerMessageDecoder extends LengthFieldBasedFrameDecoder{
	
	public ServerMessageDecoder() {
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

        Path path = AuthManager.getUserFolder(ctx.channel());
        if (path != null)
        	return decoder.decodeTransference(frame, AuthManager.getUserFolder(ctx.channel()).toString());
        else
        	return decoder.decodeTransference(frame, null);
		
	}
}
