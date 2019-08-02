package net.neferett.tests;

import net.neferett.coreengine.CoreEngine;
import org.junit.Test;

public class CorePluginsTests {

    public String config = ResourcesGetter.getConfigPath("config.json");

    @Test
    public void pluginFound() {
        CoreEngine main = new CoreEngine(this.config, false);
        CoreEngine.setInstance(main);
        main.createProcessors();

        assert main.getProcessors().getPlugins().size() != 0;
    }

    @Test
    public void enablePlugins() {
        CoreEngine main = new CoreEngine(this.config, false);
        CoreEngine.setInstance(main);
        main.createProcessors();
        assert main.getProcessors().getPlugins().size() != 0;
    }

    @Test
    public void disablePlugins() {
        CoreEngine main = new CoreEngine(this.config, false);
        CoreEngine.setInstance(main);
        main.createProcessors();
        assert main.getProcessors().getPlugins().size() != 0;
        main.getProcessors().disablePlugins();
    }

}
