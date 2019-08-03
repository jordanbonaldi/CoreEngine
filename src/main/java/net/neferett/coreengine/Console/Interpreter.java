package net.neferett.coreengine.Console;

import lombok.Data;
import lombok.SneakyThrows;
import net.neferett.coreengine.Processors.Plugins.Commands.Command;
import net.neferett.coreengine.Processors.Plugins.Commands.CommandsManager;
import net.neferett.coreengine.Processors.Plugins.Commands.ExtendableCommand;
import net.neferett.coreengine.Processors.Logger.Logger;

import java.util.Arrays;

@Data
public class Interpreter {

    private boolean activated;

    private final CommandsManager commandsManager;

    private String getCommandName(String command) {
        return command.contains(" ") ? command.split(" ")[0] : command;
    }

    private String[] getCommandsArgs(String command) {
        if (!command.contains(" "))
            return new String[]{};

        String args[] = command.split(" ");

        if (args.length == 2)
            return new String[]{args[1]};

        return Arrays.copyOfRange(args, 1, args.length);
    }

    @SneakyThrows
    public void handleCommand(String command) {
        String commandName = this.getCommandName(command);
        String[] commandArgs = this.getCommandsArgs(command);

        ExtendableCommand manager = this.commandsManager.getManager().stream().filter(e -> e.getCommand().name().equalsIgnoreCase(commandName)).findFirst().orElse(null);

        if (null == manager) {
            Logger.log("Command " + commandName + " not found. Type 'help' for help.");
            return;
        }

        Command cmd = manager.getCommand();

        if ((cmd.minLength() == 0 && cmd.argsLength() != commandArgs.length) ||
            (cmd.minLength() > 0 && commandArgs.length < cmd.minLength())
        ){
            Logger.log("Command " + cmd.name() + " mismatch args length");
            Logger.log(cmd.help());
            return;
        }

        if (cmd.activated())
            if (!manager.onCommand(commandArgs))
                Logger.log("Error while performing command");
    }

}
