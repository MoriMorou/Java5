package ru.morou;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;
import org.apache.log4j.Logger;

public class BlackBox {

    private static int PORT = 8080;
    private static int MAX_OBJ_SIZE = 1024 * 1024 * 100; //100mb

    private static final Logger logger = Logger.getLogger(BlackBox.class);

    public BlackBox() {
    }

    public void run () throws Exception {
        // Creates the EventLoopGroup
        // 1 Первая группа потоков используется для получения клиентского соединения.
        EventLoopGroup mainGroup = new NioEventLoopGroup();
        // 2 Вторая группа потоков используется для фактических операций c данными.
        EventLoopGroup wokerGroup = new NioEventLoopGroup();
        try {
            // Creates the EventLoopGroup
            // 3.2 Создаем вспомогательный класс Bootstrap, который представляет собой серию настроек для нашего Сервера
            ServerBootstrap b = new ServerBootstrap();
            b.group(mainGroup, wokerGroup)
                    // В качестве канала используется NioServerSocketChannel
                    .channel(NioServerSocketChannel.class)
                    // Для каждого клиента создается обрабодчик childHandler
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        protected void initChannel(SocketChannel ch) throws Exception {
                            // Для каждого клиента строим конвеер pipeline() и добавляем обработчик
                            ch.pipeline().addLast(
                                    new ObjectDecoder (MAX_OBJ_SIZE, ClassResolvers.cacheDisabled(null)),
                                    new ObjectEncoder (),
									new ServerHandler ()
                            );
                        }
                    })
                    .option(ChannelOption.SO_BACKLOG, 128)
//                    .option(ChannelOption.TCP_NODELAY, true)
                    .childOption(ChannelOption.SO_KEEPALIVE, true);
            // Установка связи и начало принятия входящиго соединения.
            ChannelFuture future = b.bind(PORT).sync();
            // Дождаться закрытия сокета сервера.
            future.channel().closeFuture().sync();
        } finally {
            mainGroup.shutdownGracefully();
            wokerGroup.shutdownGracefully();
        }
    }
}
