package net.neferett.coreengine;

public class Main {

    public static void main(String[] args) {
        CoreEngine.setInstance(new CoreEngine(args[0], Boolean.valueOf(args[1])));
        CoreEngine.getInstance().build();
    }

}
