package net.neferett.coreengine.Commands;

import net.neferett.coreengine.CoreEngine;
import net.neferett.coreengine.Processors.Logger.Logger;
import net.neferett.coreengine.Processors.Logger.LoggerChannel;
import net.neferett.coreengine.Processors.Plugins.Commands.Command;
import net.neferett.coreengine.Processors.Plugins.Commands.ExtendableCommand;

@Command(name = "channel", argsLength = 1, desc = "Set Channel Logger")
public class ChannelCommand extends ExtendableCommand{

    @Override
    public boolean onCommand(String... args) {

        Logger.log("Trying change your channel to " + args[0]);

        LoggerChannel chan = CoreEngine.getInstance().getLoggerChannelManager().getChannel(args[0]);

        if (null == chan) {
            Logger.log("Channel "+ args[0] +" doesn't exists.");

            return false;
        }

        CoreEngine.getInstance().getLoggerChannelManager().setActual(args[0]);

        Logger.logInChannel("Channel changed to " + args[0], args[0]);

        return true;
    }
}
