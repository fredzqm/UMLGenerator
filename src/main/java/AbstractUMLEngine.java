import analyzer.IAnalyzer;
import analyzer.IASystemModel;
import generator.IGenerator;
import runner.IRunner;

import java.io.IOException;

public abstract class AbstractUMLEngine implements Runnable {

    @Override
    public void run() {
        // get the system model
        IASystemModel systemModel = createSystemModel();

        // analyze
        IAnalyzer analyzer = createAnalyzer();
        systemModel = analyzer.analyze(systemModel);

        // generate
        IGenerator generator = createGenerator();
        String graphVisStr = generator.generate(systemModel);

        // run graphviz to generate the image
        IRunner runner = createRunner();
        try {
            runner.execute(graphVisStr);
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public abstract IRunner createRunner();

    public abstract IGenerator createGenerator();

    public abstract IAnalyzer createAnalyzer();

    public abstract IASystemModel createSystemModel();

}
