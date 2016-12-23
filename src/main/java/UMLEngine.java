import config.Configuration;
import generator.GraphVizGenerator;
import generator.ISystemModel;
import model.SystemModel;
import runner.GraphVizRunner;

import analyzer.Analyzer;
import analyzer.IAnalyzer;

public class UMLEngine extends AbstractUMLEngine {
	private Configuration config;

	public UMLEngine(Configuration configuration) {
		config = configuration;
	}

	public ISystemModel createSystemModel() {
		return SystemModel.getInstance(config);
	}

	public IAnalyzer createAnalyzer() {
		return new Analyzer();
	}

	public GraphVizGenerator createGenerator() {
		return new GraphVizGenerator(config);
	}

	public GraphVizRunner createRunner() {
		return new GraphVizRunner(config);
	}

}
