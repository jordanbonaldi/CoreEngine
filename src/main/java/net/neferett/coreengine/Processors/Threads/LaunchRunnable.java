package net.neferett.coreengine.Processors.Threads;

import lombok.Data;
import lombok.SneakyThrows;
import net.neferett.coreengine.CoreEngine;
import net.neferett.coreengine.Processors.Plugins.Handlers.PluginProcessors;

@Data
public class LaunchRunnable implements Runnable {

    private Actions action;

    public interface Actions {
        void atLaunch();
    }

    @Override
    @SneakyThrows
    public void run() {
        while (true) {
            Thread.sleep(1000);

            PluginProcessors processors = CoreEngine.getInstance().getProcessors();

            if (processors == null || processors.getProcessors() == null)
                continue;

            if (processors.getPlugins().size() == processors.getProcessors().getPluginActivated()) {
                this.action.atLaunch();
                break;
            }
        }
    }
}
