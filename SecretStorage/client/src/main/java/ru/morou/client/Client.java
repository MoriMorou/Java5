package ru.morou.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * ■ Экземпляр Bootstrap создается для инициализации клиента.
 * ■ Экземпляр NioEventLoopGroup назначается для обработки события, который включает в себя создание новых соединений и
 * обработку входящих и исходящих связанные данные.
 * ■ InetSocketAddress создается для подключения к серверу.
 * ■ EchoClientHandler будет установлен в конвейере, когда соединение установлено.
 * ■ После того, как все настроено, вызывается Bootstrap.connect () для подключения к удаленный пэр
 */


public class Client {
    private final String host;
    private final int port;


    public Client(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public void start() throws Exception {
        EventLoopGroup group = new NioEventLoopGroup ();
        try {
            //Создает "загрузочный ремешок"
            Bootstrap b = new Bootstrap ();
                    //Определяет EventLoopGroup для обработки клиентских событий; Нужна реализация NIO.
                    b.group (group)
                    //Тип канала для транспорта NIO.
                    .channel (NioSocketChannel.class)
                    //Добавляет клиент-обработчик в конвейер при создании канала
                    .handler (new ClientInitializer());
            //Подключается к удаленному пиру; ожидает завершения подключения
            ChannelFuture f = b.connect (host, port).sync ();
            //Блокирует пока канал не закрыт
            f.channel ().closeFuture ().sync ();
        } finally {
            //Выключение пулов потоков и освобождение всех ресурсов
            group.shutdownGracefully ().sync ();
        }
    }

    public static void main(String[] args) throws Exception {
//        if (args.length != 2) {
//            System.err.println ("Usage: " + Client.class.getSimpleName () + " <host> <port>");
//            return;
//        }
//        String host = args[0];
//        int port = Integer.parseInt (args[1]);

        new Client ("localhost",8080).start ();
    }
}
