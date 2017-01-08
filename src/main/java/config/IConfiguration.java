package config;

import analyzer.IAnalyzer;
import analyzer.IAnalyzerConfiguraton;
import analyzerClassParser.IClassParserConfiguration;
import generator.IGenerator;
import generator.IGeneratorConfiguration;
import model.IModelConfiguration;
import runner.IRunnerConfiguration;

public interface IConfiguration extends IRunnerConfiguration, IGeneratorConfiguration, IModelConfiguration,
		IAnalyzerConfiguraton, IClassParserConfiguration {

	Iterable<Class<? extends IAnalyzer>> getAnalyzers();

	Class<? extends IGenerator> getGenerator();

}
