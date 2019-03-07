import org.json.JSONObject;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class MainApp {
    public static void main(String[] args) {
        // JSONObject jsonObject = PackageBuilder.createFile();
        // System.out.println(jsonObject);

        JSONObject pack = PackageBuilder.renameFile("1.txt", "2.txt");
        System.out.println(pack);

        if(pack.getString("type").equals("rename")) {
            try {
                Files.move(Paths.get(pack.getString("oldPath")), Paths.get(pack.getString("newPath")), StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
