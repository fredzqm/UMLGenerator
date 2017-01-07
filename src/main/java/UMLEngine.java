import config.Configuration;
import generator.GraphVizGenerator;
import generator.IGenerator;
import generator.ISystemModel;
import model.SystemModel;
import runner.GraphVizRunner;
import runner.IRunner;

import java.io.IOException;

import analyzer.Analyzer;
import analyzer.IAnalyzer;
import analyzer.IASystemModel;

public class UMLEngine extends AbstractUMLEngine {
	private Configuration config;

	public UMLEngine(Configuration configuration) {
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
	void runGraphViz(String graphVisStr) {
		IRunner runner = new GraphVizRunner(config);
		try {
			runner.execute(graphVisStr);
		} catch (IOException | InterruptedException e) {
			throw new RuntimeException(e);
		}
	}

}
