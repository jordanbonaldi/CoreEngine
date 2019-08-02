package net.neferett.coreengine.Commands;

import net.neferett.coreengine.CoreEngine;
import net.neferett.coreengine.Processors.Plugins.Commands.Command;
import net.neferett.coreengine.Processors.Plugins.Commands.ExtendableCommand;
import net.neferett.coreengine.Processors.Logger.Logger;

@Command(name = "help", argsLength = 0, desc = "Display all commands")
public class HelpCommand extends ExtendableCommand{

    private void getCommand(ExtendableCommand command) {
        Logger.log(command.getCommand().name() + " - " + command.getCommand().desc());
    }

    @Override
    public boolean onCommand(String... args) {

        Logger.log("Commands available : ");

        CoreEngine.getInstance().getCommandsManager().getManager().forEach(this::getCommand);

        return true;
    }
}
