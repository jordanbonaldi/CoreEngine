package net.neferett.coreengine.Processors.Logger;

import lombok.Data;
import lombok.experimental.Delegate;
import java.util.HashMap;

@Data
public class LoggerChannel {

    private final String channelName;

    @Delegate
    private HashMap<String, Boolean> messages = new HashMap<>();

}
