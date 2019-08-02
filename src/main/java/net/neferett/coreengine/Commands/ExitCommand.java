package net.neferett.coreengine.Commands;

import net.neferett.coreengine.CoreEngine;
import net.neferett.coreengine.Processors.Plugins.Commands.Command;
import net.neferett.coreengine.Processors.Plugins.Commands.ExtendableCommand;
import net.neferett.coreengine.Processors.Logger.Logger;

@Command(name = "exit", argsLength = 0, desc = "Shutdown CoreEngine")
public class ExitCommand extends ExtendableCommand{

    @Override
    public boolean onCommand(String... args) {

        Logger.log("Trying to shutdown all plugins");

        CoreEngine.getInstance().getProcessors().disablePlugins();

        CoreEngine.getInstance().getInterpreter().setActivated(false);

        CoreEngine.getInstance().getPluginExecutor().setActivated(false);

        return true;
    }
}
