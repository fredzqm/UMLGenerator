package app;

import config.CommandLineParser;
import config.Configuration;
import config.RunnerConfiguration;
import display.Display;

public class Application {
    public static void main(String[] args) throws Exception {
        CommandLineParser c = new CommandLineParser(args);
        Configuration config = c.create();

        Runnable engine = UMLEngine.getInstance(config);
        engine.run();

        Display.showWindow(RunnerConfiguration.class.cast(config.createConfiguration(RunnerConfiguration.class)));
    }
}
