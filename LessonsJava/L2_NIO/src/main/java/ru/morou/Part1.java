package ru.morou;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;


public class Part1 {

    public static void main(String[] args) throws IOException {
/**
 * В Java 7 был введён пакет java.nio.file для лучшей поддержки и обработки символьных ссылок, полного доступа к
 * атрибутам и работы с файловой системой через интерфейсы или классы, такие как Path, Paths, and Files.
 */

        // Cоздание объекта Path через вызов статического метода get() класса Paths
        Path files = Paths.get("text/text2/text.txt");

        //Вывод инормации о файле
        System.out.println("Printing file information: ");
        System.out.println("\t file name: " + files.getFileName());
        System.out.println("\t root of the path: " + files.getRoot());
        System.out.println("\t parent of the target: "
                + files.getParent());
//        System.out.println(dataPath.getName(0)); //0, 1 то есть две папки перед файлом
//        System.out.println(dataPath.getName(dataPath.getNameCount()-1));
//        System.out.println(dataPath.getNameCount());
//        System.out.println(dataPath.getParent());
//        System.out.println(dataPath.toAbsolutePath());
//        System.out.println(dataPath.toFile());
//        System.out.println(dataPath.normalize()); //нормализация указанного пути
//        System.out.println(Files.list(Paths.get(("text/text2/"))));
//        System.out.println(Files.size(Paths.get(("text/text2/text.txt"))));
//        Files.write(myfile, new byte[] {65, 66, 67}, StandardOpenOption.APPEND); // добавили АВС в файл
//        Files. и много разных вариантов
//
/**
 * toUri() возвращает URI (путь который может быть открыт из браузера).
 *
 * toAbsolutePath() возвращает абсолютный путь от данного относительного пути. В случае, если был введён
 * абсолютный путь, метод вернёт его же.
 *
 * normalize() выполняет нормализацию пути, другими словами удаляет ненужные символы (такие как “ . ” и “ .. ”)
 * из объекта Path.
 *
 * toRealPath () возвращает абсолютный путь от полученного пути (как toAbsolutePath ()) и нормализует его (как
 * normalize()). Кроме того, если все параметры выбраны правильно, то он может даже работать с символьными ссылками.
 * Однако, для этого метода необходимо, чтобы конечный файл/каталог существовал в файловой системе (это не является
 * обязательным условием для других методов Path).
 */

/**
 * Если нужно обойти каталог, найти файлы, их изменить или удалить, то можно использовать интерфейс new FileVisitor<Path>
 */
        Path myfile2 = Paths.get("textNew");
        Files.walkFileTree(myfile2, new FileVisitor<Path>() {
            @Override
            public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                System.out.println(file);
                if(file.getFileName().toString().equals("text3.txt")) {
                    System.out.println("Нашли файл");
                    return FileVisitResult.TERMINATE; // останавливаем обход
                }
                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
                return null;
            }

            @Override
            public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
                return FileVisitResult.CONTINUE;
            }
        });

/**
 * Если нужно реализовать только часть методов интерфейса FileVisitor, можно использовать SimpleFileVisitor
 */
        Path myfile = Paths.get("text");
//        Files.walkFileTree(myfile, new SimpleFileVisitor<Path>() {
//            @Override
//            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
//                System.out.println("Deleted the file:" + file);
//                Files.delete(file);
//                return FileVisitResult.CONTINUE;
//            }
//
//            @Override
//            public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
//                System.out.println("Deleted the directory:" + dir);
//                Files.delete(dir);
//                return FileVisitResult.CONTINUE;
//            }
//        });
/**
 * Если нужно скопировать без использования copy (то есть копируем байты из файла out в in
 * RandomAccessFile - открытие файла для чтения или записи
 *
 * Channel бывает:
 * 1. FileChannel
 * 2. Datagrow (UDP)
 * 3. Socket (TCP)
 * 4. ServerSocket
 */
//        RandomAccessFile srcFile = new RandomAccessFile("textNew/out.txt", "rw");
//        // для этого нужно открыть канал для двух файлов
//        FileChannel srcFileChannel = srcFile.getChannel();
//
//        RandomAccessFile dstFile = new RandomAccessFile("textNew/in.txt", "rw");
//        FileChannel dstFileChannel = dstFile.getChannel();
//
//        long possition = 0; // с какого байта
//        long count = srcFileChannel.size(); // и сколько все байт нужно перекинуть
//
//        dstFileChannel.transferFrom(srcFileChannel, possition, count);
//        srcFileChannel.transferTo(possition, count, dstFileChannel);

/**
 * Как работать через буфер (продолжение в part2)
 */
//        RandomAccessFile raf = new RandomAccessFile("textNew/data.txt", "rw");
//        FileChannel fileChannel = raf.getChannel(); //получаем канал
//
//        ByteBuffer byteBuffer = ByteBuffer.allocate(4); // создаем буфер через allocate и указываем максимальный размер
//
//        int bytesRead = fileChannel.read(byteBuffer); // из канала данные попадают в буфер
//        while (bytesRead != -1) {
//            byteBuffer.flip();  // flip отчевает за переключение режима у буфера из режима я прочитал и забрал данные
//            // в режим чтения данных и передачи например на печать (см цинкл ниже)
//            while (byteBuffer.hasRemaining()){
//                System.out.print((char)byteBuffer.get());
//            }
//            System.out.println(" - кусок файла");
//            byteBuffer.clear();
//            bytesRead=fileChannel.read(byteBuffer);
//        }
    }
}