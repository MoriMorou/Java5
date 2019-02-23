package com.flamexander.netty.servers.blockserver;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.util.Arrays;

public class FirstHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf buf = (ByteBuf)msg;
        if (buf.readableBytes() < 3) {
            return;
        }
        byte[] data = new byte[3];
        buf.readBytes(data);
        System.out.println(Arrays.toString(data));
        ctx.fireChannelRead(data);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
