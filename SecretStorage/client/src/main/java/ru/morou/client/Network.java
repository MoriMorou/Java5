package ru.morou.client;

import io.netty.handler.codec.serialization.ObjectDecoderInputStream;
import io.netty.handler.codec.serialization.ObjectEncoderOutputStream;
import ru.morou.api.AbstractMessage;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.Socket;
import java.nio.file.Paths;
import java.util.Properties;

public class Network {
    private static Socket socket;
    private static ObjectEncoderOutputStream out;
    private static ObjectDecoderInputStream in;


//    private void readClientProperties() {
//        try (Reader in = new InputStreamReader(this.getClass().getResourceAsStream("/client.properties"))) {
//            Properties properties = new Properties();
//            properties.load(in);
//            host = properties.getProperty("host");
//            port = Integer.parseInt(properties.getProperty("port"));
//            localFolder = Paths.get(properties.getProperty("folder"));
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }





    public static void start() {
        try {
            socket = new Socket("localhost", 8080);
            out = new ObjectEncoderOutputStream(socket.getOutputStream());
            in = new ObjectDecoderInputStream(socket.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void stop() {
        try {
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static boolean sendMsg(AbstractMessage msg) {
        try {
            out.writeObject(msg);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static AbstractMessage readObject() throws ClassNotFoundException, IOException {
        Object obj = in.readObject();
        return (AbstractMessage) obj;
    }
}