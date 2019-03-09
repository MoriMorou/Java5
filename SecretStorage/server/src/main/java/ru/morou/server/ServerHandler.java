package ru.morou.server;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * ChannelHandler - абстракный класс обработчика событий
 * ChannelInboundHandlerAdapter - что ChannelHandler может безопасно использоваться несколькими каналами
 */


@ChannelHandler.Sharable
public class ServerHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println ("Client connected...");
    }

    //Записывает полученное сообщение отправителю, не сбрасывая исходящие сообщения.
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

    }

    //Сбрасывает ожидающие сообщения на удаленный узел и закрывает канал
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.writeAndFlush (Unpooled.EMPTY_BUFFER).addListener (ChannelFutureListener.CLOSE);
    }

    //Переопределение exceptionCaught () позволяет вам реагировать на любые подтипы Throwable: здесь вы регистрируете
    // исключение и закрываете соединение
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        //Prints the exception stack trace
        cause.printStackTrace ();
        ctx.close ();
    }
}