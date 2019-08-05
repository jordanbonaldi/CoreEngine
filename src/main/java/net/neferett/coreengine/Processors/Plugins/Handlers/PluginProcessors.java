package net.neferett.coreengine.Processors.Plugins.Handlers;

import lombok.Data;
import net.neferett.coreengine.CoreEngine;
import net.neferett.coreengine.Processors.Config.CoreConfig;
import net.neferett.coreengine.Processors.Plugins.ExtendablePlugin;
import net.neferett.coreengine.Processors.Threads.TaskProcessors;
import net.neferett.coreengine.Processors.Logger.Logger;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Data
public class PluginProcessors {

    private final String pluginPath;

    private List<ExtendablePlugin> plugins = new ArrayList<>();

    private List<Object> objects = new ArrayList<>();

    private File folder;

    private TaskProcessors processors;

    private void loadFolder() {
        this.folder = new File(this.pluginPath);
    }

    private void addPlugins(List<File> files) {
        PluginConstructor constructor = new PluginConstructor(files).build();

        //ExtendablePlugin pl = constructor.getPlugin();

        //Logger.log("Plugin " + file.getName() + " found");

        this.plugins = new ArrayList<>(constructor.getPlugins().values());

    }

    private void initiateProcessors() {
        this.processors = new TaskProcessors(CoreConfig.getConfig().getThreads());
    }

    public ExtendablePlugin getPlugin(String name) {
        return plugins.stream().filter(e -> e.getName().equalsIgnoreCase(name)).findFirst().orElse(null);
    }

    public void loadPlugins() {
        this.initiateProcessors();
        this.loadFolder();

        this.loadAll();
    }

    private void loadAll() {
        if (null == this.folder.listFiles()) {
            Logger.log("No plugins found");
            return;
        }

        this.addPlugins(Arrays.stream(Objects.requireNonNull(this.folder.listFiles())).filter(e -> e.getName().contains(".jar")).collect(Collectors.toList()));
    }

    public ExtendablePlugin enablePlugin(String name) {
        File file = Arrays.stream(Objects.requireNonNull(this.folder.listFiles())).filter(e -> e.getName().equalsIgnoreCase(name + ".jar")).findFirst().orElse(null);

        if (null == file)
            return null;

        this.addPlugins(new ArrayList<File>() {{
            add(file);
        }});

        return this.plugins.stream().filter(e -> e.getName().equalsIgnoreCase(name)).findFirst().orElse(null);
    }

    public void reloadAll() {
        this.plugins.forEach(ExtendablePlugin::disable);

        while (this.processors.getExecutorService().getActiveCount() != 0);

        this.plugins.clear();
        this.processors.getTaskProcessesList().clear();
        this.loadAll();
        this.loadTaskedPlugins();
        this.invoke();
    }

    private void invoke() {
        CoreEngine.getInstance().getPluginExecutor().invokeAll();
    }

    private PluginThread createTaskProcessByPlugin(ExtendablePlugin plugin) {
        return new PluginThread(plugin);
    }

    public void addInProcessors(ExtendablePlugin plugin) {
        this.processors.add(this.createTaskProcessByPlugin(plugin));
    }

    public void loadTaskedPlugins() {
        this.plugins.forEach(this::addInProcessors);
    }

    public void disablePlugins() {
        this.plugins.forEach(e -> e.setActivated(false));
    }

}
