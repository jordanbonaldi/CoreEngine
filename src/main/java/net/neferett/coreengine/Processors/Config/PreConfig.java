package net.neferett.coreengine.Processors.Config;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import lombok.SneakyThrows;
import net.neferett.coreengine.Utils.FileUtils;
import net.neferett.coreengine.Processors.Logger.Logger;
import org.json.JSONObject;

@Data
public class PreConfig {

    private final String path;

    private final Class<? extends Config> clazz;

    private Config config;

    private JSONObject obj;

    private String content;

    private ObjectMapper mapper;

    private String name;

    private void loadMapper() {
        this.mapper = new ObjectMapper();
    }

    public PreConfig loadPath() {
        Logger.log("Loading configuration ");
        this.content = FileUtils.getFileInfo(this.path);
        this.loadObject();

        return this;
    }

    public PreConfig loadObject() {
        Logger.log("Converting configuration ");
        this.obj = new JSONObject(this.content);

        return this;
    }

    @SneakyThrows
    public PreConfig loadClazz() {
        this.loadMapper();
        this.config = this.mapper.readValue(this.obj.toString(), this.clazz);
        Logger.log("Configuration succesfully loaded");

        return this;
    }

}
