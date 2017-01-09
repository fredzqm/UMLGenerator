package app;
import analyzer.ISystemModel;
import analyzer.IAnalyzer;
import config.Configuration;
import config.IConfiguration;
import generator.IGenerator;
import generator.IGraph;
import model.SystemModel;
import runner.GraphVizRunner;
import runner.IRunner;

public class UMLEngine extends AbstractUMLEngine {
	private IConfiguration config;

	private UMLEngine(IConfiguration configuration) {
		config = configuration;
	}

	@Override
	public ISystemModel createSystemModel() {
		return SystemModel.getInstance(config);
	}

	@Override
	ISystemModel analyze(ISystemModel systemModel) {
		Iterable<Class<? extends IAnalyzer>> anClassLs = config.getAnalyzers();
		for (Class<? extends IAnalyzer> anClass : anClassLs) {
			try {
				IAnalyzer analyzer = anClass.newInstance();
				systemModel = analyzer.analyze(systemModel, config);
			} catch (InstantiationException | IllegalAccessException e) {
				throw new RuntimeException("Analyzer " + anClass + " does not have an empty constructor", e);
			}
		}
		return systemModel;
	}

	@Override
	String generate(IGraph graph) {
		Class<? extends IGenerator> genClass = config.getGenerator();
		IGenerator gen;
		try {
			gen = genClass.newInstance();
		} catch (InstantiationException | IllegalAccessException e) {
			throw new RuntimeException("Generator " + genClass + " does not have an empty constructor", e);
		}
		return gen.generate(config, graph);
	}

	@Override
	void executeRunner(String graphVisStr) {
		IRunner runner = new GraphVizRunner(config);
		try {
			runner.execute(graphVisStr);
		} catch (Exception e) {
			throw new RuntimeException("[ INFO ]: Ensure that GraphViz bin folder is set in the environment variable.",
					e);
		}
	}

	public static UMLEngine getInstance(Configuration config) {
		return new UMLEngine(config);
	}

}
