package net.neferett.coreengine.Processors.Plugins.Handlers;

import lombok.Data;
import lombok.SneakyThrows;
import net.neferett.coreengine.Processors.Config.PluginConfig;
import net.neferett.coreengine.Processors.Plugins.ExtendablePlugin;
import net.neferett.coreengine.Processors.Plugins.Plugin;
import net.neferett.coreengine.Processors.Logger.Logger;

import java.io.File;
import java.lang.reflect.Constructor;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Arrays;
import java.util.Objects;

@Data
class PluginConstructor {

    private final File file;

    private ExtendablePlugin plugin;

    private Class<?> clazz;

    private Constructor<?> constructor;

    private URLClassLoader loader;

    private PluginConfig pluginConfig;

    @SneakyThrows
    private void buildLoader() {
        this.loader = URLClassLoader.newInstance(new URL[]{ this.file.toURI().toURL() }, this.getClass().getClassLoader());
    }

    private String getPluginPath() {
        String[] path = this.file.getAbsolutePath().split("/");

        path[path.length - 1] = null;

        StringBuilder builder = new StringBuilder();

        Arrays.stream(path).filter(Objects::nonNull).forEach(e -> builder.append("/").append(e));

        return builder.toString().substring(1) + "/";
    }

    private void extractPluginAnnotation() {
        Plugin pl = this.clazz.getAnnotation(Plugin.class);

        this.plugin.setName(pl.name());
        this.plugin.setConfigPath(pl.configPath());
        this.plugin.setActivated(pl.activated());
        this.plugin.setPluginPath(this.getPluginPath());
        this.plugin.setFileName(this.getFile().getName());
    }

    @SneakyThrows
    private void instantiationPlugin() {
        this.constructor = this.clazz.getConstructors()[0];

        this.plugin = (ExtendablePlugin) this.constructor.newInstance();

        this.extractPluginAnnotation();
    }

    @SneakyThrows
    private void buildPlugin() {
        this.pluginConfig = new ManifestReader(this.loader).build().getConfig();

        this.clazz = Class.forName(this.pluginConfig.getMain(), true, this.loader);
    }

    PluginConstructor build() {

        Logger.log("Loading plugin " + this.file.getName());

        this.buildLoader();
        this.buildPlugin();
        this.instantiationPlugin();

        return this;
    }

}
