package ru.morou;

import java.io.IOException;
import java.net.InetSocketAddress;


import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import ru.morou.JavaFX.Controller;
import ru.morou.JavaFX.controllers.ScreenManager;
import ru.morou.handlers.ClientChannelInboundHandlerAdapter;
import ru.morou.handlers.ClientMessageDecoder;
import ru.morou.handlers.TransferMessageEncoder;
import ru.morou.processors.StandardTransference;

/**
 * Клиент Netty
 * @author morou
 */
public class BlueBox {
	
	private static final int PORT = 8080;
	private static final String HOST = "localhost";
	
	private Channel currentChannel = null;
	
	private static BlueBox INSTANCE = null;
	
	private BlueBox() {}
	
	public static BlueBox getInstance() {
		if (INSTANCE == null || INSTANCE.currentChannel == null || !INSTANCE.currentChannel.isActive()) {
			INSTANCE = new BlueBox ();
			Thread t = new Thread(() -> {
				try {
					INSTANCE.start();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			});
			t.setDaemon(true); 
			t.start();
		}
		return INSTANCE;
	}
    
    /**
     * Соединение с сервером
     * @throws InterruptedException
     */
	public void start() throws InterruptedException  {

		EventLoopGroup group = new NioEventLoopGroup();
		try {
			Bootstrap clientBootstrap = new Bootstrap();
			clientBootstrap.group(group);
			clientBootstrap.channel(NioSocketChannel.class);
			clientBootstrap.remoteAddress(new InetSocketAddress("localhost", 8080));
			clientBootstrap.handler(new ChannelInitializer<SocketChannel>() {
				protected void initChannel(SocketChannel socketChannel) throws Exception {
					socketChannel.pipeline().addLast(
							new ClientMessageDecoder(),
							new TransferMessageEncoder(),
							new ClientChannelInboundHandlerAdapter());
					currentChannel = socketChannel;
				}
			});
			ChannelFuture channelFuture = clientBootstrap.connect().sync();
			channelFuture.channel().closeFuture().sync();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				group.shutdownGracefully().sync();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * Отправка сообщения на сервер
	 * @param data сообщение
	 * @throws IOException
	 * @throws InterruptedException 
	 */
	public void sendData(StandardTransference data) throws IOException, InterruptedException {
		
		if (INSTANCE.currentChannel == null || !INSTANCE.currentChannel.isActive()) {
			// если нет открытого канала - переходим на экран аутотентификации
			ScreenManager.showLoginScreen();
			Controller.throwAlertMessage("Error", "You are unauthorized or connection to server lost");
			return;
		}
		INSTANCE.currentChannel.writeAndFlush(data);
	}
	
	public void disconnect() {
		currentChannel.close();
	}

}
