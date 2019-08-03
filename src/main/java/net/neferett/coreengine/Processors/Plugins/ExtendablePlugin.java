package net.neferett.coreengine.Processors.Plugins;

import lombok.Data;
import net.neferett.coreengine.CoreEngine;
import net.neferett.coreengine.Processors.Plugins.Commands.ExtendableCommand;
import net.neferett.httpserver.api.Routing.RoutingProperties;

import java.util.ArrayList;
import java.util.List;

@Data
public abstract class ExtendablePlugin implements CorePlugin{

    private String name;

    private String configPath;

    private boolean activated;

    private String pluginPath;

    private String fileName;

    private List<ExtendableCommand> commands = new ArrayList<>();

    public void disable() {
        this.setActivated(false);
    }

    protected void addCommand(Class<? extends ExtendableCommand> clazz) {
        this.commands.add(this.getEngine().getCommandsManager().createCommand(this.name, clazz));
    }

    protected void addRoute(Class<? extends RoutingProperties> clazz) {
        this.getEngine().getHttpServerAPI().addRoute(clazz);
    }

    protected String getConfigPath() {
        return this.pluginPath + this.configPath;
    }

    protected CoreEngine getEngine() {
        return CoreEngine.getInstance();
    }

    protected void createChannel(String channel) {
        this.getEngine().addChannel(channel);
    }
}
