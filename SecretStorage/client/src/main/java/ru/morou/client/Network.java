package ru.morou.client;


import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;
import ru.morou.api.ResultAction;
import ru.morou.client.controllers.LoginController;

import java.net.InetSocketAddress;

public class Network {
    private static Network ourInstance = new Network();

    public static Network getInstance() {
        return ourInstance;
    }

    private Network(){
    }

    // текущий канал от клиента к серверу
    private Channel currentChannel;

    public Channel getCurrentChannel() {
        return currentChannel;
    }

    public void start(ResultAction actionAfterAuth) {
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            //Создает "загрузочный ремешок"
            Bootstrap clientBootstrap = new Bootstrap ();
            //Определяет EventLoopGroup для обработки клиентских событий; Нужна реализация NIO.
            clientBootstrap.group (group);
            //Тип канала для транспорта NIO.
            clientBootstrap.channel (NioSocketChannel.class);
            //Обозначаем порт и хост
            clientBootstrap.remoteAddress(new InetSocketAddress("localhost", 8189));
            //Добавляет клиент-обработчик в конвейер при создании канала
            clientBootstrap.handler((ClientInitializer) (socketChannel)-> {
                socketChannel.pipeline().addLast(new ObjectDecoder(10*1024*1024,
                        ClassResolvers.cacheDisabled(null)), new ObjectEncoder(),
                        new AuthHandler(actionAfterAuth));
                currentChannel = socketChannel;
            });

            ChannelFuture channelFuture = clientBootstrap.connect().sync();
            channelFuture.channel().closeFuture().sync();
        } catch (Exception e){
            e.printStackTrace();
        }finally {
            try {
                group.shutdownGracefully().sync();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void sendData(Object data) {
        currentChannel.writeAndFlush(data);
    }

    // определиться с контроллерами
    // FIXME: 5/9/2019 хрен знает (пока путаница)
    public void startHandler(LoginController controller) {
        currentChannel.pipeline().addLast(new MainHandler(controller));
    }

    public boolean isConnectionOpened() {
        return currentChannel != null && currentChannel.isActive();
    }

    public void closeConnection() {
        currentChannel.close();
    }
}
