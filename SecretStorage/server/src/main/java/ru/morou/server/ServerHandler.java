package ru.morou.server;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.ReferenceCountUtil;
import ru.morou.api.CommandMessage;
import ru.morou.api.FileMessage;
import ru.morou.api.FilePartitionWorker;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;

public class ServerHandler extends ChannelInboundHandlerAdapter {

//    private static final Logger logger = Logger.getLogger(ServerHandler.class.getName();

    private String clientName;

    public ServerHandler (String clientName) {
        this.clientName = clientName;
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        try {
            if (msg == null){
                return;
            }
            // FIXME: 5/7/2019  add logger data
            handleReceivedMessage(ctx, msg);
        } finally {
            ReferenceCountUtil.release(msg);
        }
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        // FIXME: 5/7/2019  add logger data
        ctx.close();
    }

    /**
     * Метод для обработки полученных сообщений
     * @param ctx
     * @param msg
     * @throws Exception
     */
    public void handleReceivedMessage(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (msg instanceof CommandMessage) {
            CommandMessage commandMessage = (CommandMessage) msg;
            if (commandMessage.getType() == CommandMessage.CMD_MSG_REQUEST_FILES_LIST) {
                ServerUtilities.sendFilesList(ctx.channel(), clientName);
            }
            if (commandMessage.getType() == CommandMessage.CMD_MSG_REQUEST_SERVER_DELETE_FILE) {
                Files.delete(ServerUtilities.getFilePathInRepository(clientName, (String) commandMessage.getAttachment()[0]));
                ServerUtilities.sendFilesList(ctx.channel(), clientName);
            }
            if (commandMessage.getType() == CommandMessage.CMD_MSG_REQUEST_FILE_DOWNLOAD) {
                try {
                    FilePartitionWorker.sendFile(Paths.get(((File) commandMessage.getAttachment()[0]).getAbsolutePath()), ctx.channel());
                } catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
        if (msg instanceof FileMessage) {
            FileMessage fileMessage = (FileMessage) msg;
            // FIXME: 5/8/2019 ДОПИСАТЬ
            FilePartitionWorker.savePathToFile(ServerUtilities.getUserRootPath(clientName), fileMessage, () -> ServerUtilities.sendFilesList(ctx.channel(), clientName));
        }
    }
}
