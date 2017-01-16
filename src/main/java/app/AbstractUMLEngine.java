package app;

import analyzer.utility.ISystemModel;
import generator.IGraph;

/**
 * TODO: FRED documentation.
 */
public abstract class AbstractUMLEngine implements Runnable {
    @Override
    public void run() {
        // get the system model
        ISystemModel systemModel = createSystemModel();

        // analyze
        systemModel = analyze(systemModel);

        // generate
        String graphVisStr = generate(systemModel);

        // run graphviz to generate the image
        executeRunner(graphVisStr);
    }

    abstract ISystemModel createSystemModel();

    abstract ISystemModel analyze(ISystemModel systemModel);

    abstract String generate(IGraph systemModel);

    abstract void executeRunner(String graphVisStr);
}
