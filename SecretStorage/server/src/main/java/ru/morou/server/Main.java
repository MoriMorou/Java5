package ru.morou.server;


import ru.morou.server.Service.NettyServer;

public class Main {
    public static void main(String[] args) throws Exception{
        new NettyServer().run();
    }
}
