

import config.Configuration;
import display.Display;
import generator.GraphVizGenerator;
import generator.IGenerator;
import model.SystemModel;
import runner.GraphVizRunner;
import runner.IRunner;

import java.io.IOException;

public class UMLEngine implements Runnable {
    private Configuration config;

    public UMLEngine(Configuration configuration) {
        config = configuration;
    }

    @Override
    public void run() {
        // get the system model
        SystemModel systemModel = SystemModel.getInstance(config);

        // analyze

        // generate
        IGenerator generator = new GraphVizGenerator(config, config.getParserKey());
        String graphVisStr = generator.generate(systemModel, null);

        // run graphviz to generate the image
        IRunner runner = new GraphVizRunner();
        try {
            runner.execute(config, graphVisStr);
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }

        // Display a small window
        Display.showWindown(config);

    }

}
