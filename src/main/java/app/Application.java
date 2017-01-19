package app;

import config.CommandLineParser;
import config.Configuration;
import config.EngineConfiguration;
import config.IEngineConfiguration;
import config.RunnerConfiguration;
import viewer.Viewer;

public class Application {
    public static void main(String[] args) throws Exception {
        CommandLineParser c = new CommandLineParser(args);
        Configuration confi = c.create();

        IEngineConfiguration config = IEngineConfiguration.class
                .cast(confi.createConfiguration(EngineConfiguration.class));
        // IEngineConfiguration config = null;
        Runnable engine = UMLEngine.getInstance(config);
        engine.run();

        Runnable viewer = new Viewer(
                RunnerConfiguration.class.cast(config.createConfiguration(RunnerConfiguration.class)));
        viewer.run();
    }
}
