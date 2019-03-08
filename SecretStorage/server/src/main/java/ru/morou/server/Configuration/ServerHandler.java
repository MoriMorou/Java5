package ru.morou.server.Configuration;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;

/**
 * ChannelHandler - абстракный класс обработчика событий
 * ChannelInboundHandlerAdapter - что ChannelHandler может безопасно использоваться несколькими каналами
 */


@ChannelHandler.Sharable
public class ServerHandler extends ChannelInboundHandlerAdapter {

    //Записывает полученное сообщение отправителю, не сбрасывая исходящие сообщения.
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf in = (ByteBuf) msg;
        System.out.println("Server received: " + in.toString(CharsetUtil.UTF_8));
        ctx.write(in);
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