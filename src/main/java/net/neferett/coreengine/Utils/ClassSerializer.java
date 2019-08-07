package net.neferett.coreengine.Utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.json.JSONObject;

public class ClassSerializer {

    @SneakyThrows
    public static JSONObject serialize(Object obj) {
        return new JSONObject(new ObjectMapper().writeValueAsString(obj));
    }
}
