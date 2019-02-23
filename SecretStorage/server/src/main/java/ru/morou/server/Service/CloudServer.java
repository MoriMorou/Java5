package ru.morou.server.Service;

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


public class CloudServer {

    private String HOST;
    private int DEFAULT_PORT;
    private Path folder;
    private static final int MAX_OBJ_SIZE = 1024 * 1024 * 10;


    // Читаем парамметры, которые необходимы для подключения сервера

    private void readServerProperties(){
        try (Reader in = new InputStreamReader(this.getClass().getResourceAsStream("/server.properties"))){
            Properties properties = new Properties();
            properties.load(in);
            HOST = properties.getProperty("host");
            DEFAULT_PORT = Integer.parseInt(properties.getProperty("port"));
            folder= Paths.get(properties.getProperty("folder"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void run() {
        // Первая группа потоков используется для получения клиентского соединения.
        EventLoopGroup mainGroup = new NioEventLoopGroup();
        // Вторая группа потоков используется для фактических операций c данными.
        EventLoopGroup wokerGroup = new NioEventLoopGroup();
        try {
            readServerProperties();
            ServerBootstrap b = new ServerBootstrap();
            b.group(mainGroup, wokerGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler((ChannelInitializer)(socketChannel) -> (
                            socketChannel.pipeline().addLast(
                            ))
        }






    }
}
