package app;

import config.CommandLineParser;
import config.Configuration;
import config.RunnerConfiguration;
import runner.ExplorerRunner;

public class Application {
    public static void main(String[] args) throws Exception {
        CommandLineParser c = new CommandLineParser(args);
        Configuration config = c.create();

        Runnable engine = UMLEngine.getInstance(config);
        engine.run();

        config.set(RunnerConfiguration.EXECUTABLE_PATH, "explorer");
        ExplorerRunner explorerRunner = new ExplorerRunner(RunnerConfiguration.class.cast(config.createConfiguration(RunnerConfiguration.class)));
        explorerRunner.execute(null);
    }
}
