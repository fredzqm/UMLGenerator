package app;

import analyzer.utility.ISystemModel;

/**
 * TODO: FRED documentation.
 */
public abstract class AbstractUMLEngine implements Runnable {
    @Override
    public final void run() {
        // get the system model
        ISystemModel systemModel = createSystemModel();

        // analyze
        analyze(systemModel);

        // generate
        String graphVisStr = generate(systemModel);

        // run graphviz to generate the image
        executeRunner(graphVisStr);
    }

    abstract ISystemModel createSystemModel();

    abstract void analyze(ISystemModel systemModel);

    abstract String generate(ISystemModel systemModel);

    abstract void executeRunner(String graphVisStr);
}
