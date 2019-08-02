package net.neferett.coreengine.Processors.Logger;

import lombok.Data;
import lombok.experimental.Delegate;

import java.util.HashMap;

@Data
public class LoggerChannelManager {

    @Delegate
    private HashMap<String, LoggerChannel> channelList = new HashMap<>();

    private String actual;

    public LoggerChannel getChannel(String channel) {
        return this.getChannelList().get(channel);
    }

}