package ru.morou.api;

import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

public class FilePartitionWorker {

    public static final int PART_SIZE = 8189; // 8 килобайт

    // FIXME: 5/8/2019 переделать, проблема с большими файлами (нужно через FileInputStream)
    public static void sendFile(Path path, Channel out){
        try {
//            byte[] dataFile = new byte[PART_SIZE];
            byte[] fileData = Files.readAllBytes(path);
            int partCount = fileData.length / PART_SIZE;
            if (fileData.length % PART_SIZE != 0 ) {
                partCount++;
            }
            for (int i = 0; i < partCount; i++) {
                int startPosition = i * PART_SIZE;
                int endPosition = (i + 1) * PART_SIZE;
                if (endPosition > fileData.length) {
                    endPosition = fileData.length;
                }
                FileMessage fileMessage = new FileMessage(path.getFileName().toString(), Arrays.copyOfRange(fileData, startPosition, endPosition), partCount, i);
                ChannelFuture channelFuture = out.writeAndFlush(fileMessage);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     *
     * @param rootPath
     * @param fileMessage
     * @param finalAction
     */
    public static void savePathToFile(String rootPath, FileMessage fileMessage, FileWritingFinally finalAction){
        RandomAccessFile randomAccessFile = null;
        FileChannel outChannel = null;
        try {
            Path path = Paths.get(rootPath + fileMessage.getFileame());
            if (!Files.exists(path)){
                Files.createFile(path);
            }
            randomAccessFile = new RandomAccessFile(path.toFile(), "rw");
            outChannel = randomAccessFile.getChannel();
            outChannel.position(fileMessage.getPartNumber() * FilePartitionWorker.PART_SIZE);
            ByteBuffer byteBuffer = ByteBuffer.allocate(fileMessage.getData().length);
            byteBuffer.put(fileMessage.getData());
            byteBuffer.flip();
            outChannel.write(byteBuffer);
            byteBuffer.clear();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                outChannel.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if(fileMessage.getPartNumber() + 1 == fileMessage.getPartsCount()) {
            finalAction.action();
        }
    }
}
