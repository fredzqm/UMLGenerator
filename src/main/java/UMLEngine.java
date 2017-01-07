import config.Configuration;
import generator.IGenerator;
import generator.ISystemModel;
import model.SystemModel;
import runner.GraphVizRunner;
import runner.IRunner;

import analyzer.IAnalyzer;
import analyzer.IASystemModel;

public class UMLEngine extends AbstractUMLEngine {
	private Configuration config;

	private UMLEngine(Configuration configuration) {
		config = configuration;
	}

	@Override
	public IASystemModel createSystemModel() {
		return SystemModel.getInstance(config);
	}

	@Override
	IASystemModel analyze(IASystemModel systemModel) {
		Iterable<Class<? extends IAnalyzer>> anClassLs = config.getAnalyzers();
		for (Class<? extends IAnalyzer> anClass : anClassLs) {
			IAnalyzer analyzer;
			try {
				analyzer = anClass.newInstance();
				systemModel = analyzer.analyze(systemModel);
			} catch (InstantiationException | IllegalAccessException e) {
				throw new RuntimeException("Analyzer " + anClass + " does not have an empty constructor", e);
			}
		}
		return systemModel;
	}

	@Override
	String generate(ISystemModel systemModel) {
		Class<? extends IGenerator> genClass = config.getGenerator();
		IGenerator gen;
		try {
			gen = genClass.newInstance();
		} catch (InstantiationException | IllegalAccessException e) {
			throw new RuntimeException("Generator " + genClass + " does not have an empty constructor", e);
		}
		return gen.generate(config, systemModel);
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
