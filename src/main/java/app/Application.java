package app;

import config.CommandLineParser;
import config.IEngineConfiguration;
import config.RunnerConfiguration;
import viewer.Viewer;

public class Application {
    public static void main(String[] args) throws Exception {
        CommandLineParser c = new CommandLineParser(args);
        IEngineConfiguration config = c.create();

        Runnable engine = UMLEngine.getInstance(config);
        engine.run();

        Runnable viewer = new Viewer(
                RunnerConfiguration.class.cast(config.createConfiguration(RunnerConfiguration.class)));
        viewer.run();
    }
}
