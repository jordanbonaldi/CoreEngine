package net.neferett.coreengine.Processors.Threads;

import net.neferett.coreengine.Processors.Plugins.ExtendablePlugin;

public interface TaskProcess extends Runnable{

    void start();

    void end();

    ExtendablePlugin getPlugin();

}
