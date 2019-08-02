package net.neferett.coreengine.Commands;

import net.neferett.coreengine.CoreEngine;
import net.neferett.coreengine.Processors.Logger.Logger;
import net.neferett.coreengine.Processors.Plugins.Commands.Command;
import net.neferett.coreengine.Processors.Plugins.Commands.ExtendableCommand;
import net.neferett.coreengine.Processors.Plugins.ExtendablePlugin;

@Command(name = "disable", argsLength = 1, desc = "Disable a plugin")
public class DisableCommand extends ExtendableCommand {

    @Override
    public boolean onCommand(String... args) {

        Logger.log("Trying to disable plugin " + args[0]);

        ExtendablePlugin plugin = CoreEngine.getInstance().getProcessors().getPlugin(args[0]);

        if (null == plugin) {
            Logger.log("Plugin " + args[0] + " doesn't exists !");
            return false;
        }

        plugin.disable();

        plugin.setActivated(false);

        return true;
    }

}
