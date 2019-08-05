package net.neferett.coreengine.Utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.json.JSONObject;

import java.io.*;

public class ClassSerializer {

    @SneakyThrows
    public static JSONObject serialize(Object obj) {
        return new JSONObject(new ObjectMapper().writeValueAsString(obj));
    }

    public static <T> T castObj(Object o) throws IOException, ClassNotFoundException {
        if (o != null) {
            ByteArrayOutputStream baous = new ByteArrayOutputStream();
            {
                ObjectOutputStream oos = new ObjectOutputStream(baous);
                try {
                    oos.writeObject(o);
                } finally {
                    try {
                        oos.close();
                    } catch (Exception e) {
                    }
                }
            }

            byte[] bb = baous.toByteArray();
            if (bb != null && bb.length > 0) {
                ByteArrayInputStream bais = new ByteArrayInputStream(bb);
                ObjectInputStream ois = new ObjectInputStream(bais);
                T res = (T) ois.readObject();
                return res;
            }
        }
        return null;
    }

}
