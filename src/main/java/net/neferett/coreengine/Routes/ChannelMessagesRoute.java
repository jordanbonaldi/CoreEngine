package net.neferett.coreengine.Routes;

import com.sun.net.httpserver.HttpExchange;
import net.neferett.coreengine.CoreEngine;
import net.neferett.httpserver.api.Routing.Route;
import net.neferett.httpserver.api.Routing.RoutingProperties;
import org.json.JSONObject;

@Route(name = "/channel", params = {"channel"})
public class ChannelMessagesRoute extends RoutingProperties {

    @Override
    public JSONObject routeAction(HttpExchange t)
    {
        String channel = this.params.get("channel");

        JSONObject obj = new JSONObject();

        CoreEngine.getInstance().getLoggerChannelManager().getChannel(channel).getMessages().forEach(
                (message, read) ->
                  obj.put(message, read ? "Read" : "Unread")

        );

        return obj;
    }

}
