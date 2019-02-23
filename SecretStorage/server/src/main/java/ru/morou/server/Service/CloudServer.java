package ru.morou.server.Service;

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
        readServerProperties();

    }
}
