package ru.morou.client;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;

public class ClientInitializer extends ChannelInitializer<SocketChannel> {

    private static final int MAX_OBJ_SIZE = 1024 * 1024 * 100; // 100Mb

    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        ChannelPipeline pipeline = socketChannel.pipeline ();

        pipeline.addLast ("encoder", new ObjectEncoder ())
                .addLast ("decoder", new ObjectDecoder (MAX_OBJ_SIZE, ClassResolvers.cacheDisabled(null)))
                .addLast ("handler", new ClientHandler ());
    }
}
