package net.neferett.coreengine.Commands;

import net.neferett.coreengine.CoreEngine;
import net.neferett.coreengine.Processors.Logger.Logger;
import net.neferett.coreengine.Processors.Plugins.Commands.Command;
import net.neferett.coreengine.Processors.Plugins.Commands.ExtendableCommand;

@Command(name = "reload", argsLength = 0, desc = "reload one or all plugins")
public class ReloadCommand extends ExtendableCommand{

    @Override
    public boolean onCommand(String... args) {
        Logger.log("Reloading all plugins...");

        CoreEngine.getInstance().getProcessors().reloadAll();

        return true;
    }
}
