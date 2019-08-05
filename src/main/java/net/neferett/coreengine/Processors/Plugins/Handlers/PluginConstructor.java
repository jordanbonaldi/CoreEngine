package net.neferett.coreengine.Processors.Plugins.Handlers;

import lombok.Data;
import lombok.SneakyThrows;
import net.neferett.coreengine.CoreEngine;
import net.neferett.coreengine.Processors.Config.PluginConfig;
import net.neferett.coreengine.Processors.Plugins.ExtendablePlugin;
import net.neferett.coreengine.Processors.Plugins.Plugin;
import net.neferett.coreengine.Processors.Logger.Logger;

import java.io.File;
import java.lang.reflect.Constructor;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

@Data
class PluginConstructor {

    private final List<File> files;

    private HashMap<String, ExtendablePlugin> plugins = new HashMap<>();

    private Class<?> clazz;

    private Constructor<?> constructor;

    private URLClassLoader loader;

    private PluginConfig pluginConfig;

    @SneakyThrows
    private URL[] getUrlFromList() {
        return this.files.stream().map(e -> {
            URL url = null;
            try {
                url =  e.toURI().toURL();
            } catch (MalformedURLException e1) {
                e1.printStackTrace();
            }
            return url;
        }).toArray(URL[]::new);
    }

    @SneakyThrows
    private void buildLoader() {
        this.loader = URLClassLoader.newInstance(getUrlFromList(), CoreEngine.class.getClassLoader());
    }

    private String getPluginPath(File file) {
        String[] path = file.getAbsolutePath().split("/");

        path[path.length - 1] = null;

        StringBuilder builder = new StringBuilder();

        Arrays.stream(path).filter(Objects::nonNull).forEach(e -> builder.append("/").append(e));

        return builder.toString().substring(1) + "/";
    }

    private void extractPluginAnnotation(ExtendablePlugin plugin, File file) {
        Plugin pl = this.clazz.getAnnotation(Plugin.class);

        plugin.setName(pl.name());
        plugin.setConfigPath(pl.configPath());
        plugin.setActivated(pl.activated());
        plugin.setPluginPath(this.getPluginPath(file));
        plugin.setFileName(file.getName());
    }

    @SneakyThrows
    private void instantiationPlugin(File file) {

        this.constructor = this.clazz.getConstructors()[0];

        ExtendablePlugin pl = (ExtendablePlugin) this.constructor.newInstance();

        this.plugins.put(file.getName(), pl);

        this.extractPluginAnnotation(pl, file);
    }

    @SneakyThrows
    private void buildPlugin(File file) {
        this.pluginConfig = new ManifestReader(new URLClassLoader(new URL[]{file.toURI().toURL()})).build().getConfig();

        this.clazz = Class.forName(this.pluginConfig.getMain(), true, this.loader);
    }

    PluginConstructor build() {

        this.buildLoader();

        this.files.forEach(e -> {
            Logger.log("Loading plugin " + e.getName());

            {
                this.buildPlugin(e);
                this.instantiationPlugin(e);
            }

        });




        return this;
    }

}
