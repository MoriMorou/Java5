package ru.morou.client;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import ru.morou.api.FileListMessage;
import ru.morou.api.FileMessage;
import ru.morou.api.FilePartitionWorker;
import ru.morou.client.controllers.WorkController;

public class MainHandler extends ChannelInboundHandlerAdapter {

    private WorkController controller;

    public MainHandler(WorkController controller) {
        this.controller = controller;
    }

    // FIXME: 5/9/2019 прямая привязка к интерфейсу (нужно через call back)
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if(msg == null) {
            return;
        }
        if(msg instanceof FileListMessage) {
            controller.refreshCloudFilesTable((FileListMessage) msg);
        }
        if(msg instanceof FileMessage) {
            FileMessage fileMessage = (FileMessage) msg;
            FilePartitionWorker.savePathToFile("local_storage/", fileMessage, () -> controller.refreshLocalFilesTable());
        }
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
        controller.closeConnectionWithServer();
    }
}
