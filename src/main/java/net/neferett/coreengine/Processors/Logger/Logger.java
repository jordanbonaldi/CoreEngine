package net.neferett.coreengine.Processors.Logger;

import lombok.NonNull;
import net.neferett.coreengine.CoreEngine;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Logger {

    public static void log(String msg) {
        Calendar cal = Calendar.getInstance();
        final SimpleDateFormat date = new SimpleDateFormat("hh:mm:ss");
        System.out.println("[" + date.format(cal.getTime()) + "] " + msg);
    }

    public static void logInChannel(String msg, @NonNull String channel)
    {
        LoggerChannel chan = CoreEngine.getInstance().getLoggerChannelManager().getChannel(channel);

        if (null == chan) {
            LoggerChannel __chan = new LoggerChannel(channel);
            __chan.add(msg);

            CoreEngine.getInstance().addChannel(__chan);
            return;
        }

        if (channel.equalsIgnoreCase(CoreEngine.getInstance().getLoggerChannelManager().getActual())) {
            chan.getMessages().forEach(Logger::log);
            log(msg);
            chan.getMessages().clear();
        } else
            chan.add(msg);
    }

}
