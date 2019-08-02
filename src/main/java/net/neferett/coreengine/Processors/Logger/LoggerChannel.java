package net.neferett.coreengine.Processors.Logger;

import lombok.Data;
import lombok.experimental.Delegate;
import net.neferett.coreengine.Utils.Adder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Data
public class LoggerChannel {

    private final String channelName;

    @Delegate
    private HashMap<String, Boolean> messages = new HashMap<>();

}
