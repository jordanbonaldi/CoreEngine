package net.neferett.coreengine;

import lombok.*;
import net.neferett.coreengine.Console.Interpreter;
import net.neferett.coreengine.Console.ThreadedInterpreter;
import net.neferett.coreengine.Processors.Config.CoreConfig;
import net.neferett.coreengine.Processors.Config.PreConfig;
import net.neferett.coreengine.Processors.Logger.LoggerChannel;
import net.neferett.coreengine.Processors.Logger.LoggerChannelManager;
import net.neferett.coreengine.Processors.Plugins.Commands.CommandsManager;
import net.neferett.coreengine.Processors.Plugins.CorePlugin;
import net.neferett.coreengine.Processors.Plugins.Handlers.PluginProcessors;
import net.neferett.coreengine.Processors.Plugins.PluginThreadExecutor;
import net.neferett.coreengine.Processors.Threads.LaunchRunnable;
import net.neferett.httpserver.api.HTTPServerAPI;

import java.util.Date;

@Data
public class CoreEngine {

    private PreConfig preConfig;

    private CoreConfig config;

    private PluginProcessors processors;

    private PluginThreadExecutor pluginExecutor;

    private Thread pluginThreadExecutor;

    private Thread interpreterThread;

    private CommandsManager commandsManager;

    private Interpreter interpreter;

    private LoggerChannelManager loggerChannelManager;

    private HTTPServerAPI httpServerAPI;

    private boolean interactive;

    private long beforeUsedMemory;

    private Date launch;

    @NonNull
    private String configPath;

    public CoreEngine(String configPath, boolean interactive) {
        this.configPath = configPath;
        this.interactive = interactive;
        this.beforeUsedMemory = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
        instance = this;

        this.createChannelManager();
        this.addChannel("Core");
        this.getLoggerChannelManager().setActual("Core");

        this.loadConfig();
    }

    public long calculateMemory() {
        return (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory() - this.beforeUsedMemory) / 1024;
    }

    private void initializeConfig() {
        this.preConfig = new PreConfig(this.configPath, CoreConfig.class);
    }

    private void loadConfig() {
        this.initializeConfig();

        this.config = (CoreConfig) this.preConfig.loadPath().loadClazz().getConfig();
    }

    private void loadRoutes() {
        this.config.getRoutes().forEach((e) ->
            this.httpServerAPI.addAllRoutesInPath("net.neferett.coreengine.Routes", e)
        );
    }

    private void initHttpServerAPI() {
        this.httpServerAPI = new HTTPServerAPI(this.config.getPort(), this.config.getThreads());
        this.loadRoutes();
    }

    @SneakyThrows
    public void createProcessors() {
        this.processors = new PluginProcessors(this.config.getPluginsPath(this.preConfig.getPath()));

        this.processors.loadPlugins();
        this.processors.loadTaskedPlugins();
    }

    public CorePlugin getPlugin(Class<?> clazz) {
        return this.processors.getPlugins().stream().filter(e -> e.getClass().equals(clazz)).findFirst().orElse(null);
    }

    private void loadCommandsManager() {
        this.commandsManager = new CommandsManager();

        this.commandsManager.searchCommandsInSubPackages();
    }

    private void initiateInterpreter() {
        this.interpreter = new Interpreter(this.getCommandsManager());
        this.interpreter.setActivated(true);
    }

    public Thread createInterpreterThread() {
        return new Thread(new ThreadedInterpreter(this.interpreter));
    }

    private void createChannelManager() {
        this.loggerChannelManager = new LoggerChannelManager();
    }

    public void addChannel(String channel) {
        if (null == this.loggerChannelManager.getChannel(channel))
            this.loggerChannelManager.put(channel, new LoggerChannel(channel));
    }

    public void addChannel(LoggerChannel channel) {
        this.loggerChannelManager.put(channel.getChannelName(), channel);
    }

    public void actionAfterAllPluginLoaded() {
        LaunchRunnable runnable = new LaunchRunnable();

        runnable.setAction(() -> this.httpServerAPI.start());
        new Thread(runnable).start();
    }

    @Getter
    @Setter
    private static CoreEngine instance;

    void build() {

        this.launch = new Date();

        this.actionAfterAllPluginLoaded();

        System.out.println(this.launch);

        { // HTTPServer
            this.initHttpServerAPI();
        }

        {   // Handling Processing
            this.createProcessors();
            if (this.isInteractive()) {
                this.loadCommandsManager();
                this.initiateInterpreter();
            }
        }

        {  // Loading Interpreter
            if (this.isInteractive()) {
                this.interpreterThread = this.createInterpreterThread();
                this.interpreterThread.start();
            }
        }

        {  // Cached Thread Pool !!! NOTHING AFTER !!!
            this.pluginExecutor = new PluginThreadExecutor(this.processors);

            this.pluginThreadExecutor = new Thread(this.pluginExecutor);

            this.pluginThreadExecutor.start();
        }
    }

}
