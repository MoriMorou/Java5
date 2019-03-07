import org.json.JSONObject;

public class PackageBuilder {
    public static JSONObject createFile() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("type", "file");
        jsonObject.put("data", new byte[]{1, 2, 3, 4, 5});
        return jsonObject;
    }

    public static JSONObject renameFile(String oldPath, String newPath) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("type", "rename");
        jsonObject.put("oldPath", oldPath);
        jsonObject.put("newPath", newPath);
        return jsonObject;
    }
}
