package net.neferett.coreengine.Processors.Logger;

import lombok.NonNull;
import net.neferett.coreengine.CoreEngine;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class Logger {

    private static void print(String msg) {
        final Calendar cal = Calendar.getInstance();
        final SimpleDateFormat date = new SimpleDateFormat("hh:mm:ss");

        System.out.println("[" + date.format(cal.getTime()) + "] " + msg);
    }

    public static void log(String msg) {
        final String channel = CoreEngine.getInstance().getLoggerChannelManager().getActual();
        final LoggerChannel logger = CoreEngine.getInstance().getLoggerChannelManager().getChannel(channel);

        logger.put(msg, true);
        print(msg);
    }

    public static void logInChannel(String msg, @NonNull String channel)
    {
        LoggerChannel chan = CoreEngine.getInstance().getLoggerChannelManager().getChannel(channel);

        if (null == chan) {
            LoggerChannel __chan = new LoggerChannel(channel);
            __chan.put(msg, false);

            CoreEngine.getInstance().addChannel(__chan);
            return;
        }

        if (channel.equalsIgnoreCase(CoreEngine.getInstance().getLoggerChannelManager().getActual())) {
            chan.getMessages().forEach((message, read) -> { if(!read) log(message); });
            log(msg);
        } else
            chan.put(msg, false);
    }

}
