package net.neferett.tests;

import net.neferett.coreengine.CoreEngine;
import org.junit.Test;

public class CoreConfigTests {

    public String config = ResourcesGetter.getConfigPath("config.json");

    @Test
    public void coreFound() {
        assert new CoreEngine(this.config, false).getConfig().getPluginConfig() != null;
    }

    @Test
    public void configGoodValues() {
        assert new CoreEngine(this.config, false).getConfig().getPluginConfig().equals("manifest.json");
    }

}