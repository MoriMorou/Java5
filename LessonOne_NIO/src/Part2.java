import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class Part2 {

    public static void main(String[] args) {
        ByteBuffer byteBuffer = ByteBuffer.allocate(4); // создаем буфер через allocate и указываем максимальный размер

    }

    public static void fileRead() throws Exception{
    RandomAccessFile raf = new RandomAccessFile("textNew/data.txt", "rw");
    FileChannel fileChannel = raf.getChannel(); //получаем канал

    ByteBuffer byteBuffer = ByteBuffer.allocate(4); // создаем буфер через allocate и указываем максимальный размер

    int bytesRead = fileChannel.read(byteBuffer); // из канала данные попадают в буфер
        while (bytesRead != -1) {
            byteBuffer.flip();  // flip отчевает за переключение режима у буфера из режима я прочитал и забрал данные
            // в режим чтения данных и передачи например на печать (см цинкл ниже)
            while (byteBuffer.hasRemaining()) {
                System.out.print((char) byteBuffer.get());
            }
            System.out.println(" - кусок файла");
            byteBuffer.clear();
            bytesRead = fileChannel.read(byteBuffer);
        }
    }
}
