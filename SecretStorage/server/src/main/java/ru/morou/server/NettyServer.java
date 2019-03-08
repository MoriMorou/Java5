package ru.morou.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import ru.morou.server.Configuration.ServerHandler;

import java.net.InetSocketAddress;

/**
 * ■ Канал-сокеты
 * ■ EventLoop - поток управления, многопоточность, параллелизм
 * ■ ChannelFuture - асинхронное уведомление
 */


public class NettyServer {
    private int port;

    public NettyServer(int port) {
        this.port = port;
    }

    public static void main(String[] args) throws Exception {
        new NettyServer(8080).start();
    }

    public void start() throws Exception {
        final ServerHandler serverHandler = new ServerHandler();
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            ServerBootstrap b = new ServerBootstrap();
            b.group(group)
                    .channel(NioServerSocketChannel.class)
                    .localAddress(new InetSocketAddress(port))
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        public void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline().addLast(serverHandler);
                        }
                    });

            ChannelFuture f = b.bind(8080).sync();
            System.out.println(NettyServer.class.getName() +
                    " started and listening for connections on " + f.channel().localAddress());
            f.channel().closeFuture().sync();
        } finally {
            group.shutdownGracefully().sync();
        }
    }

//        // Creates the EventLoopGroup
//        // 1 Первая группа потоков используется для получения клиентского соединения.
//        EventLoopGroup mainGroup = new NioEventLoopGroup();
//        // 2 Вторая группа потоков используется для фактических операций c данными.
//        EventLoopGroup wokerGroup = new NioEventLoopGroup();
//        try {
//            // Creates the EventLoopGroup
//            // 3.1 Создаем вспомогательный класс Bootstrap, который представляет собой серию настроек для нашего Сервера
//            ServerBootstrap b = new ServerBootstrap();
//            b.group(mainGroup, wokerGroup)
//                    // В качестве транспортного канала используется NioServerSocketChannel
//                    .channel(NioServerSocketChannel.class)
//                    // Устанавливает адрес сокета, используя указанный порт
// //                   .localAddress (new InetSocketAddress (PORT))
//                    // Для каждого клиента создается обрабодчик childHandler
//                    .childHandler(new ChannelInitializer<SocketChannel>() {
//                        protected void initChannel(SocketChannel ch) throws Exception {
//                            // Для каждого клиента строим конвеер pipeline() и добавляем обработчик
//                            ch.pipeline().addLast(
////                                    new ObjectDecoder(MAX_OBJ_SIZE, ClassResolvers.cacheDisabled(null)),
////                                    new ObjectEncoder(),
//                                    new ServerHandler()
//                            );
//                        }
//                    })
//                    .option(ChannelOption.SO_BACKLOG, 128)
////                    .option(ChannelOption.TCP_NODELAY, true)
//                    .childOption(ChannelOption.SO_KEEPALIVE, true);
//            // Установка связи и начало принятия входящиго соединения.
//            ChannelFuture future = b.bind().sync();
//            // Дождаться закрытия сокета сервера.
//            future.channel().closeFuture().sync();
//        } finally {
//            mainGroup.shutdownGracefully();
//            wokerGroup.shutdownGracefully();
//        }
//    }
}