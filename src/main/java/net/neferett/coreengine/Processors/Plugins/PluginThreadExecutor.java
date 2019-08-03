package net.neferett.coreengine.Processors.Plugins;

import lombok.Data;
import net.neferett.coreengine.CoreEngine;
import net.neferett.coreengine.Processors.Logger.Logger;
import net.neferett.coreengine.Processors.Plugins.Handlers.PluginProcessors;

@Data
public class PluginThreadExecutor implements Runnable {

    private final PluginProcessors processors;

    private boolean activated = true;

    private boolean sendSignal = true;

    public void invokeAll() {
        this.sendSignal = true;
    }

    @Override
    public void run() {
        while (this.activated) {
            Logger.logInChannel("" + this.sendSignal, "debug");
            if (this.sendSignal) {
                this.sendSignal = false;
                Logger.logInChannel("refreshed", "debug");
                this.processors.getProcessors().invokeAll();
            }
        }

        this.processors.getProcessors().shutDown();
    }
}
