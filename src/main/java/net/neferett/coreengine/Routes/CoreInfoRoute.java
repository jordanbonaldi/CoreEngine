package net.neferett.coreengine.Routes;

import com.sun.net.httpserver.HttpExchange;
import lombok.Data;
import net.neferett.coreengine.CoreEngine;
import net.neferett.coreengine.Processors.Plugins.ExtendablePlugin;
import net.neferett.coreengine.Utils.ClassSerializer;
import net.neferett.httpserver.api.Routing.Route;
import net.neferett.httpserver.api.Routing.RoutingProperties;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Route(name = "/info")
public class CoreInfoRoute extends RoutingProperties {

    @Data
    private class DisplayJson {

        private final String name;

        private final String now;

        private final String launchedTime;

        private final long runningTime;

        private final long memoryUsed;

        private final int pluginsLoaded;

        private final int channels;

        private final List<String> channelsName;

        private final List<String> pluginsName;
    }

    @Override
    public JSONObject routeAction(HttpExchange t)
    {
        Date now = new Date();
        DisplayJson display = new DisplayJson(
                "CoreEngine",
                now.toString(),
                CoreEngine.getInstance().getLaunch().toString(),
                (now.getTime() - CoreEngine.getInstance().getLaunch().getTime()) / 1000,
                CoreEngine.getInstance().calculateMemory(),
                CoreEngine.getInstance().getProcessors().getPlugins().size(),
                CoreEngine.getInstance().getLoggerChannelManager().size(),
                new ArrayList<>(CoreEngine.getInstance().getLoggerChannelManager().getChannelList().keySet()),
                CoreEngine.getInstance().getPluginExecutor().getProcessors().getPlugins().stream().map(ExtendablePlugin::getName).collect(Collectors.toList())
        );

        return ClassSerializer.serialize(display);
    }

}
