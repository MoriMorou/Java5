package ru.morou.server;

import ru.morou.server.Configuration.NettyServer;

public class Main {
    public static void main(String[] args) throws Exception{
        new NettyServer().run();
    }
}
