package net.neferett.coreengine.Processors.Plugins.Handlers;

import lombok.Data;
import lombok.SneakyThrows;
import net.neferett.coreengine.Processors.Plugins.ExtendablePlugin;
import net.neferett.coreengine.Processors.Threads.TaskProcess;
import net.neferett.coreengine.Processors.Logger.Logger;

@Data
public class PluginThread implements TaskProcess {

    private final ExtendablePlugin plugin;

    @Override
    public void start() {
        Logger.log("Enabling plugin : " + plugin.getName());
        this.plugin.onEnable();
    }

    @Override
    public void end() {
        Logger.log("Disabling plugin : " + plugin.getName());
        this.plugin.onDisable();
    }

    @Override
    public ExtendablePlugin getPlugin() {
        return this.plugin;
    }

    @Override
    @SneakyThrows
    public void run() {
        this.start();

        {
            while (plugin.isActivated()) {
                Thread.sleep(1000);
            }
        }

        this.end();
    }
}
