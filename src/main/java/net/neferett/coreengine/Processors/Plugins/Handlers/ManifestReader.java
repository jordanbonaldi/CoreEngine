package net.neferett.coreengine.Processors.Plugins.Handlers;

import lombok.Data;
import net.neferett.coreengine.Processors.Config.CoreConfig;
import net.neferett.coreengine.Processors.Config.PluginConfig;
import net.neferett.coreengine.Processors.Config.PreConfig;
import net.neferett.coreengine.Utils.FileUtils;
import net.neferett.coreengine.Processors.Logger.Logger;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URLClassLoader;

@Data
class ManifestReader {

    private final URLClassLoader loader;

    private PreConfig preConfig;

    private PluginConfig config;

    private InputStreamReader reader;

    private void createReader() {
        InputStream stream = this.loader.getResourceAsStream(CoreConfig.getConfig().getPluginConfig());

        this.reader = new InputStreamReader(stream);
    }

    private void loadPreConfig() {
        this.preConfig = new PreConfig(null, PluginConfig.class);

        this.preConfig.setContent(FileUtils.getFileInfoWithReader(this.reader));
        this.preConfig.loadObject().loadClazz();
    }

    private void createPlugin() {
        this.config = (PluginConfig) this.preConfig.getConfig();
    }

    ManifestReader build() {
        Logger.log("Getting plugins's manifest informations ...");

        this.createReader();
        this.loadPreConfig();
        this.createPlugin();

        return this;
    }

}
