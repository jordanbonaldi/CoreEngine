package net.neferett.coreengine.Commands;

import net.neferett.coreengine.CoreEngine;
import net.neferett.coreengine.Processors.Logger.Logger;
import net.neferett.coreengine.Processors.Plugins.Commands.Command;
import net.neferett.coreengine.Processors.Plugins.Commands.ExtendableCommand;
import net.neferett.coreengine.Processors.Plugins.ExtendablePlugin;

@Command(name = "enable", argsLength = 1, desc = "Enable a plugin with file name")
public class EnableCommand extends ExtendableCommand {

    @Override
    public boolean onCommand(String... args) {

        Logger.log("Trying to enable plugin " + args[0]);

        ExtendablePlugin plugin = CoreEngine.getInstance().getProcessors().enablePlugin(args[0]);

        if (null == plugin)
            Logger.log("File " + args[0] + " doesn't exists in plugins folder !");

        new Thread(() -> {
            CoreEngine.getInstance().getProcessors().addInProcessors(plugin);
            CoreEngine.getInstance().getProcessors().getProcessors().invokePlugin(args[0] + ".jar");
        }).start();
        
        return true;
    }

}
