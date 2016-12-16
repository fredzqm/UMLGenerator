package example;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import config.Configuration;
import generator.GraphVizGenerator;
import generator.IGenerator;
import generator.ISystemModel;
import model.SystemModel;
import runner.GraphVizRunner;
import runner.IRunner;
import utility.Modifier;

/**
 * A Test Class that will generate files for local inspection.
 * <p>
 * Created by lamd on 12/15/2016.
 */
public class LocalTester {
    public static void main(String[] args) {
        localTester();
    }

    private static ISystemModel setupSystemModel() {
        Configuration config = Configuration.getInstance();
        List<String> classList = new ArrayList<>();
        classList.add(GraphVizGenerator.class.getPackage().getName() + "." + GraphVizGenerator.class.getSimpleName());
        classList.add("java.lang.String");
        config.setClasses(classList);
        config.setRecursive(true);
        return SystemModel.getInstance(config);
    }

    private static void localTester() {
        // Set up the system model and config.
        ISystemModel systemModel = setupSystemModel();

        // Set up config.
        Configuration config = Configuration.getInstance();
        Set<Modifier> filters = new HashSet<>();
        filters.add(Modifier.PROTECTED);
        filters.add(Modifier.PRIVATE);
        config.setFilters(filters);
        config.setNodesep(1.0);
        config.setRecursive(true);
        config.setRankDir("BT");
        config.setOutputDirectory("./output");
        config.setFileName("testFilter");
        config.setExecutablePath("dot");

        // Create GraphVizGenerator.
        IGenerator generator = new GraphVizGenerator(config);

        String actual = generator.generate(systemModel, null);

        internalRunner(config, actual); // Comment out if you want actual files to be
    }

    /**
     * Interal Testing Runner method to call for actual output.
     *
     * @param config         Configuration for the runner to use.
     * @param graphVizString GraphViz DOT string t
     */
    private static void internalRunner(Configuration config, String graphVizString) {
        // Create the runner
        IRunner runner = new GraphVizRunner();
        try {
            runner.execute(config, graphVizString);
        } catch (Exception e) {
            System.err.println("[ INFO ]: Ensure that GraphViz bin folder is set in the environment variable.");
            e.printStackTrace();
        }
    }
}
