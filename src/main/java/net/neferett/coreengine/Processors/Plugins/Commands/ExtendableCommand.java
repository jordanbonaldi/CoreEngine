package net.neferett.coreengine.Processors.Plugins.Commands;

import lombok.Data;

@Data
public abstract class ExtendableCommand {

    private Command command;

    private String pluginName;

    public abstract boolean onCommand(String ... args);
}
