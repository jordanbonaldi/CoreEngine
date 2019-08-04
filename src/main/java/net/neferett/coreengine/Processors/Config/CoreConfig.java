package net.neferett.coreengine.Processors.Config;

import lombok.Data;
import lombok.SneakyThrows;
import net.neferett.coreengine.CoreEngine;

import java.util.Arrays;
import java.util.List;

@Data
public class CoreConfig implements Config {

    private String pluginConfig;

    private int threads;

    private int port;

    private List<String> routes;

    private boolean redis;

    private String redisIp;

    private String redisPassword;

    private int redisPort;

    @SneakyThrows
    public String getPluginsPath(String path) {
        String[] array = path.split("/");

        array[array.length - 1] = "plugins/";

        StringBuilder builder = new StringBuilder();

        Arrays.stream(array).forEach(e -> builder.append("/").append(e));

        return builder.toString().substring(1);
    }

    @SneakyThrows
    public String getCommandsPath(String path) {
        String[] array = path.split("/");

        array[array.length - 1] = "commands/";

        StringBuilder builder = new StringBuilder();

        Arrays.stream(array).forEach(e -> builder.append("/").append(e));

        return builder.toString().substring(1);
    }

    public static CoreConfig getConfig() {
        return CoreEngine.getInstance().getConfig();
    }
}
