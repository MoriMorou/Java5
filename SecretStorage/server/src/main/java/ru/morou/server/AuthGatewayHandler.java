package ru.morou.server;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.ReferenceCountUtil;
import ru.morou.api.AuthMessage;
import ru.morou.api.CommandMessage;

/**
 * ChannelHandler - абстракный класс обработчика событий
 * ChannelInboundHandlerAdapter - что ChannelHandler может безопасно использоваться несколькими каналами
 */


//@ChannelHandler.Sharable
public class AuthGatewayHandler extends ChannelInboundHandlerAdapter {

//    private static final Logger logger = Logger.getLogger(AuthGatewayHandler.class);

    private boolean authorized;

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        // FIXME: 5/7/2019  add logger data
        System.out.println ("Client connected...");
    }

    /**
     * Получаем сообщение и проходим аутентификацию
     * @param ctx
     * @param msg
     * @throws Exception
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (msg == null) {
            return;
            // FIXME: 5/7/2019  add logger data
        }
        if (authorized) {
            if (msg instanceof AuthMessage) {
                AuthMessage authMessage = (AuthMessage) msg;
                // FIXME: 5/8/2019 тут нет пока базы данных (то есть если хоть что-то вбито то ты клиент ^^)
                if (authMessage.getLogin().equals("login") && authMessage.getPassword().equals("password")) {
                    String username = "client";
                    authorized = true;
                    CommandMessage cmAuthOk = new CommandMessage(CommandMessage.CMD_MSG_AUTH_OK);
                    ctx.writeAndFlush(cmAuthOk).await();
                    // из контекста мы получаем доступ к конвееру и мы добавляем в ковеер ServerHandler
                    // это сделано что бы не закладывать постоянно имя клиента и открытый ServerHandler теперь работает
                        // с этим клиентом
                    ctx.pipeline().addLast(new ServerHandler(username));
                }
            } else {
                ReferenceCountUtil.release(msg);
            }
        } else {
            // если уже аутентификация прошла то сообщение кидаем далее
            ctx.fireChannelRead(msg);
        }
    }

    //Сбрасывает ожидающие сообщения на удаленный узел и закрывает канал
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
//        ctx.writeAndFlush (Unpooled.EMPTY_BUFFER).addListener (ChannelFutureListener.CLOSE);
        ctx.flush();
    }

    //Переопределение exceptionCaught () позволяет вам реагировать на любые подтипы Throwable: здесь вы регистрируете
    //исключение и закрываете соединение
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        //Prints the exception stack trace
        cause.printStackTrace ();
        ctx.close ();
    }
}