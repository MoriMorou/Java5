package ru.morou;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;


import java.io.IOException;
import java.net.InetSocketAddress;

/**
 * Клиент Netty
 * @author morou
 */
public class BlackBox {

	private static final int PORT = 8080;
	private static final String HOST = "localhost";
	private static int MAX_OBJ_SIZE = 1024 * 1024 * 100; //100mb

	private Channel currentChannel = null;

	private static BlackBox INSTANCE = null;

	private BlackBox() {}
	
	public static BlackBox getInstance() {
		if (INSTANCE == null || INSTANCE.currentChannel == null || !INSTANCE.currentChannel.isActive()) {
			INSTANCE = new BlackBox ();
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
							new ObjectDecoder (MAX_OBJ_SIZE, ClassResolvers.cacheDisabled(null)),
							new ObjectEncoder (),
							new ClientHandler());
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
//
//	/**
//	 * Отправка сообщения на сервер
//	 * @param msg сообщение
//	 * @throws IOException
//	 * @throws InterruptedException
//	 */
//	public static boolean sendMsg(AbstractMessage msg) {
//		try {
//			out.writeObject(msg);
//			return true;
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//		return false;
//	}
//
//	public static AbstractMessage readObject() throws ClassNotFoundException, IOException {
//		Object obj = in.readObject();
//		return (AbstractMessage) obj;
//	}
//
//	public void disconnect() {
//		currentChannel.close();
//	}

}
