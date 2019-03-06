package ru.morou;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;
//import ru.morou.handlers.AuthHandler;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;


/**
 * Сервер Netty
 * @author morou
 */
public class BlueBoxServer {

	private String HOST;
	private int DEFAULT_PORT;
	private Path FOLDER;
	private static final int MAX_OBJ_SIZE = 1024 * 1024 * 10;

	public BlueBoxServer(){

	}

	// Читаем парамметры, которые необходимы для подключения сервера
	private void readServerProperties() {
		try (Reader in = new InputStreamReader (this.getClass().getResourceAsStream("/server.properties"))) {
			Properties properties = new Properties();
			properties.load(in);
			HOST = properties.getProperty("host");
			DEFAULT_PORT = Integer.parseInt(properties.getProperty("port"));
			FOLDER = Paths.get(properties.getProperty("folder"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void run() throws Exception {
		// Creates the EventLoopGroup
		// 1 Первая группа потоков используется для получения клиентского соединения.
		EventLoopGroup mainGroup = new NioEventLoopGroup();
		// 2 Вторая группа потоков используется для фактических операций c данными.
		EventLoopGroup wokerGroup = new NioEventLoopGroup();
		try {
			// 3.1 Считываем даннае конфигурации для сервера
			readServerProperties();
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
//                                    new AuthHandler(),
									new ObjectDecoder (MAX_OBJ_SIZE, ClassResolvers.cacheDisabled(null)),
									new ObjectEncoder ()
//									new AuthHandler ()
							);
						}
					})
					.option(ChannelOption.SO_BACKLOG, 128)
//                    .option(ChannelOption.TCP_NODELAY, true)
					.childOption(ChannelOption.SO_KEEPALIVE, true);
			// Установка связи и начало принятия входящиго соединения.
			ChannelFuture future = b.bind(DEFAULT_PORT).sync();
			// Дождаться закрытия сокета сервера.
			future.channel().closeFuture().sync();
		} finally {
			mainGroup.shutdownGracefully();
			wokerGroup.shutdownGracefully();
		}
	}
}
