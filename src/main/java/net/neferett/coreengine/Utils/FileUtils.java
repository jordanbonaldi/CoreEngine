package net.neferett.coreengine.Utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import lombok.val;
import org.json.JSONObject;

import java.io.*;

public class FileUtils {

    @SneakyThrows
    public static String getFileInfo(String path) {

        val file = new File(path);

        val reader = new BufferedReader(new FileReader(file));

        return getString(reader);
    }

    @SneakyThrows
    public static String getFileInfoWithReader(Reader rd) {

        val reader = new BufferedReader(rd);

        return getString(reader);
    }

    private static String getString(BufferedReader reader) throws IOException {
        val sb = new StringBuilder();

        String s;

        while ((s = reader.readLine()) != null) {
            sb.append(s);
        }

        reader.close();

        return sb.toString();
    }

    public static String convertStreamToString(InputStream is)
    {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            StringBuilder sb = new StringBuilder();

            String line = null;

            while ((line = reader.readLine()) != null) {
                sb.append(line).append("\n");
            }

            is.close();

            return sb.toString();
        } catch (Exception ignored) {
            return null;
        }
    }

    public static Object convertStreamToClass(InputStream is, Class<?> clazz)
    {
        String ts = convertStreamToString(is);

        try {
            return new ObjectMapper().readValue(ts, clazz);
        } catch (Exception ignored) {
            return null;
        }
    }

}
