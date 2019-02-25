package ru.morou.server.Service;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.ReferenceCountUtil;
import ru.morou.server.Database.DBConnection;

import java.util.Arrays;

public class AuthHandler extends ChannelInboundHandlerAdapter {
    private boolean authorized = false;

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        DBConnection dbService = new DBConnection();
        dbService.connect();
        dbService.createTable();
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
    }
}