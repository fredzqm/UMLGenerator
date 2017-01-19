package app;

import config.CommandLineParser;
import config.Configuration;
import config.RunnerConfiguration;
import viewer.Viewer;

public class Application {
    public static void main(String[] args) throws Exception {
        CommandLineParser c = new CommandLineParser(args);
        Configuration config = c.create();

        Runnable engine = UMLEngine.getInstance(config);
        engine.run();

        Runnable viewer = new Viewer(config.createConfiguration(RunnerConfiguration.class));
        viewer.run();
    }
}
