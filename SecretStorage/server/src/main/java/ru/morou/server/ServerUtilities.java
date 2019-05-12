package ru.morou.server;

import io.netty.channel.Channel;
import ru.morou.api.FileListMessage;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Класс отвечает за работу с репозиторием и файлами
 */

public class ServerUtilities {

    /**
     * Метод отвечает за формирования списка файлов по имени пользователя
     * @param channel
     * @param username - имя пользователя
     */
    public static void sendFilesList(Channel channel, String username) {

        try {
            FileListMessage fileListMessage = new FileListMessage(Paths.get(getUserRootPath(username)));
            channel.writeAndFlush(fileListMessage);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Для простоты обращения к корню по имени пользователя
     * @param username - имя пользователя
     * @return возвращает имя репозитория
     */
    public static String getUserRootPath(String username) {
        return "repository/" + username + "/";
    }

    /**
     * Для определения пути к файлу
     * @param username имя пользователя
     * @param filename имя файла
     * @return возвращает путь к файлу
     */
    public static Path getFilePathInRepository(String username, String filename) {
        return Paths.get(getUserRootPath(username), filename);
    }
}
