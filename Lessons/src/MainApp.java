import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;


public class MainApp {

    public static void main(String[] args) throws IOException {
        Path myfile = Paths.get("text/text2/text.txt");
//        System.out.println(dataPath.getFileName());
//        System.out.println(dataPath.getName(0)); //0, 1 то есть две папки перед файлом
//        System.out.println(dataPath.getName(dataPath.getNameCount()-1));
//        System.out.println(dataPath.getNameCount());
//        System.out.println(dataPath.getParent());
//        System.out.println(dataPath.toAbsolutePath());
//        System.out.println(dataPath.toFile());
//        System.out.println(dataPath.normalize()); //нормализация указанного пути
        System.out.println(Files.list(Paths.get(("text/text2/"))));
        System.out.println(Files.size(Paths.get(("text/text2/text.txt"))));
//        Files.write(myfile, new byte[] {65, 66, 67}, StandardOpenOption.APPEND); // добавили АВС в файл
//        Files. и много разных вариантов
//
//      Если нужно обойти каталог



    }
}
