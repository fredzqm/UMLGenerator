package all;

import analyzer.Analyzer;
import analyzer.IAnalyzer;
import config.Configuration;
import dummy.RelDummyManyClass;
import generator.GraphVizGenerator;
import generator.IGenerator;
import generator.ISystemModel;
import model.SystemModel;
import runner.GraphVizRunner;
import runner.IRunner;
import utility.Modifier;

import java.util.ArrayList;
import java.util.List;

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
		classList.add(RelDummyManyClass.class.getName());
		config.setClasses(classList);
		config.setRecursive(true);
		return SystemModel.getInstance(config);
	}

	private static void localTester() {
		// Set up the system model and config.
		ISystemModel systemModel = setupSystemModel();

		// Set up config.
		Configuration config = Configuration.getInstance();
		config.setNodesep(1.0);
		config.setRecursive(true);
		config.setRankDir("BT");
		config.setOutputDirectory("./output");
		config.setFileName("localTest");
		config.setExecutablePath("dot");
		config.setOutputFormat("svg");

		IGenerator generator = new GraphVizGenerator(config);
		IAnalyzer analyzer = new Analyzer();
//		systemModel = analyzer.analyze(systemModel);

		String actual = generator.generate(systemModel);

		internalRunner(config, actual);
	}

	/**
	 * Interal Testing Runner method to call for actual output.
	 *
	 * @param config
	 *            Configuration for the runner to use.
	 * @param graphVizString
	 *            GraphViz DOT string t
	 */
	private static void internalRunner(Configuration config, String graphVizString) {
		// Create the runner
		IRunner runner = new GraphVizRunner(config);
		try {
			runner.execute(graphVizString);
		} catch (Exception e) {
			System.err.println("[ INFO ]: Ensure that GraphViz bin folder is set in the environment variable.");
			e.printStackTrace();
		}
	}
}
