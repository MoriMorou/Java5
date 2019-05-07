package ru.morou;


import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import ru.morou.handlers.ServerMessageDecoder;
import ru.morou.handlers.TransferMessageEncoder;
import ru.morou.handlers.TransferMessageHandler;

/**
 * Сервер Netty
 * @author morou
 */
public class BlueBox {
	
	private static final int  INET_PORT_NUMBER = 8189;
	private static final String      INET_HOST = "localhost";	

	public void start() throws Exception {
		
		EventLoopGroup bossGroup = new NioEventLoopGroup();
		EventLoopGroup workerGroup = new NioEventLoopGroup();

		try {
            ServerBootstrap serverBootstrap = new ServerBootstrap();
			serverBootstrap.group(bossGroup, workerGroup)
			               .channel(NioServerSocketChannel.class)
					       .childHandler(new ChannelInitializer<SocketChannel>() {
								@Override
								public void initChannel(SocketChannel socketChannel) {
									ChannelPipeline pipeline = socketChannel.pipeline();
									
									pipeline.addLast(new ServerMessageDecoder ());
									pipeline.addLast(new TransferMessageEncoder ());
									pipeline.addLast(new TransferMessageHandler ());
								}
							})
					       .option(ChannelOption.SO_BACKLOG, 100)
					       .option(ChannelOption.TCP_NODELAY, true)
					       .childOption(ChannelOption.SO_KEEPALIVE, true)
					       .bind(INET_HOST, INET_PORT_NUMBER)
				           .sync()
				           .channel()
				           .closeFuture()	
				           .syncUninterruptibly();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
	}

	public static void main(String[] args) throws Exception {
        new BlueBox ().start();
    }
}
