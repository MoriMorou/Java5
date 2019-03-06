package ru.morou.server.Configuration;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.ReferenceCountUtil;
import ru.morou.api.CodeRequest;




public class AuthHandler extends ChannelInboundHandlerAdapter {

    public boolean _isAuthorize;
    private String _login = null;


    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("Client connected...");
    }


    //метод channelRead () вызывается при получении данных.
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        try {
            if (msg == null) {
                return;
            }
            if (msg instanceof CodeRequest) {
                CodeRequest fr = (CodeRequest) msg;
//
            }
        } finally {
            ReferenceCountUtil.release(msg);
        }
    }
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
//    if (Files.exists(Paths.get("server_storage/" + fr.getFilename()))) {
//                    FileMessage fm = new FileMessage(Paths.get("server_storage/" + fr.getFilename()));
//                    ctx.writeAndFlush(fm);
//                }
}