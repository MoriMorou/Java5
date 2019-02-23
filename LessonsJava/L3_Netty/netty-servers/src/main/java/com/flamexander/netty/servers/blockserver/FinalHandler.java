package com.flamexander.netty.servers.blockserver;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.buffer.UnpooledHeapByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.io.FileOutputStream;
import java.io.FileWriter;

public class FinalHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        byte[] arr = (byte[]) msg;
        FileWriter out = new FileWriter("1.txt");
        for (int i = 0; i < 3; i++) {
            out.write("" + arr[i]);
        }
        out.close();
        System.out.println("Сообщение записано в файл");

        ctx.write("Java\n");
        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
