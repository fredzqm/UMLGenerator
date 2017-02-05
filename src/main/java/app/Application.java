package app;

import java.io.FileNotFoundException;

import config.CommandLineParser;
import config.IConfiguration;
import runner.RunnerConfiguration;
import viewer.Viewer;

public class Application {
    public static void main(String[] args) throws FileNotFoundException {
        CommandLineParser c = new CommandLineParser(args);
        IConfiguration config = c.create();

        Runnable engine = UMLEngine.getInstance(config);
        engine.run();

        Runnable viewer = new Viewer(config.createConfiguration(RunnerConfiguration.class));
        viewer.run();
    }
}
