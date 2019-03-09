package ru.morou.server;

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

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

public class NettyServer {

    private String HOST;
    private int PORT;
    private Path folder;
    private static final int MAX_OBJ_SIZE = 1024 * 1024 * 10;


    // Читаем парамметры, которые необходимы для подключения сервера

    private void readServerProperties() {
        try (Reader in = new InputStreamReader (this.getClass().getResourceAsStream("/server.properties"))) {
            Properties properties = new Properties();
            properties.load(in);
            HOST = properties.getProperty("host");
            PORT = Integer.parseInt(properties.getProperty("port"));
            folder = Paths.get(properties.getProperty("folder"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void start() throws Exception {
        // Creates the EventLoopGroup
        // 1 Первая группа потоков используется для получения клиентского соединения.
        EventLoopGroup mainGroup = new NioEventLoopGroup ();
        // 2 Вторая группа потоков используется для фактических операций c данными.
        EventLoopGroup wokerGroup = new NioEventLoopGroup();
        try {
            readServerProperties();
            // Creates the EventLoopGroup
            // 3.1 Создаем вспомогательный класс Bootstrap, который представляет собой серию настроек для нашего Сервера
            ServerBootstrap b = new ServerBootstrap();
            b.group(mainGroup, wokerGroup)
                    // В качестве транспортного канала используется NioServerSocketChannel
                    .channel(NioServerSocketChannel.class)
                    // Для каждого клиента создается обрабодчик childHandler
                    .childHandler(new ChannelInitializer<SocketChannel> () {
                        protected void initChannel(SocketChannel ch) throws Exception {
                            // Для каждого клиента строим конвеер pipeline() и добавляем обработчик
                            ch.pipeline().addLast(
                                    new ObjectDecoder (MAX_OBJ_SIZE, ClassResolvers.cacheDisabled(null)),
                                    new ObjectEncoder (),
                                    new ServerHandler()
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