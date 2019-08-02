package net.neferett.coreengine.Console;

import lombok.Data;
import lombok.SneakyThrows;
import net.neferett.coreengine.CoreEngine;
import net.neferett.coreengine.Processors.Logger.Logger;

import java.io.BufferedReader;
import java.io.InputStreamReader;

@Data
public class ThreadedInterpreter implements Runnable{

    private final Interpreter interpreter;

    @SneakyThrows
    private void readBufferedReader() {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        this.interpreter.handleCommand(reader.readLine());
    }

    @Override
    @SneakyThrows
    public void run() {

        while (true)
                if (null == CoreEngine.getInstance()
                        .getProcessors().getPlugins().stream()
                        .filter(e -> !e.isActivated()).findFirst().orElse(null))
                    break;

        Thread.sleep(2000);

        while (this.interpreter.isActivated()) {

            this.readBufferedReader();

            Thread.sleep(10);
        }

        Logger.log("Deactivating command's interpreter");

    }
}
