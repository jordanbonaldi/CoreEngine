package net.neferett.coreengine.Processors.Plugins.Commands;

import lombok.Data;
import lombok.SneakyThrows;
import lombok.experimental.Delegate;
import net.neferett.coreengine.Processors.Logger.Logger;
import org.reflections.Reflections;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;

@Data
public class CommandsManager {

    @Delegate(types = adderExtendable.class)
    public List<ExtendableCommand> manager = new ArrayList<>();

    interface adderExtendable {
        boolean add(ExtendableCommand a);
    }

    interface adderClass {
        boolean add(Class<? extends ExtendableCommand> a);
    }

    @Delegate(types = adderClass.class)
    List<Class<? extends ExtendableCommand>> clazz = new ArrayList<>();

    public void searchCommandsInSubPackages() {
        Reflections reflection = new Reflections("net.neferett.coreengine.Commands");

        reflection.getSubTypesOf(ExtendableCommand.class).forEach(e -> this.createCommand(null, e));
    }

    @SneakyThrows
    public ExtendableCommand createCommand(String name, Class<? extends ExtendableCommand> clazz) {
        Constructor constructor = clazz.getDeclaredConstructors()[0];

        ExtendableCommand extendableCommand = (ExtendableCommand) constructor.newInstance();
        extendableCommand.setCommand(clazz.getAnnotation(Command.class));
        if (null != name)
            extendableCommand.setPluginName(name);

        Logger.log("Registering new command " + extendableCommand.getCommand().name());

        this.add(extendableCommand);

        return extendableCommand;
    }
}
