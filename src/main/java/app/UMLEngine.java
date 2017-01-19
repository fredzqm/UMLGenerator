package app;

import analyzer.utility.IAnalyzer;
import analyzer.utility.ISystemModel;
import config.*;
import generator.IGenerator;
import generator.IGraph;
import model.SystemModel;
import runner.GraphVizRunner;
import runner.IRunner;

/**
 * TODO: Fred documentation.
 */
public class UMLEngine extends AbstractUMLEngine {
    private IEngineConfiguration config;

    private UMLEngine(IEngineConfiguration configuration) {
        config = configuration;
    }

    /**
     * TODO: Fred Documentation.
     *
     * @param config
     * @return
     */
    static UMLEngine getInstance(IEngineConfiguration config) {
        return new UMLEngine(config);
    }

    @Override
    public ISystemModel createSystemModel() {
        return SystemModel.getInstance(ModelConfiguration.class.cast(config.createConfiguration(ModelConfiguration.class)));
    }

    @Override
    ISystemModel analyze(ISystemModel systemModel) {
        Iterable<Class<? extends IAnalyzer>> anClassLs = this.config.getAnalyzers();
        for (Class<? extends IAnalyzer> anClass : anClassLs) {
            try {
                IAnalyzer analyzer = anClass.newInstance();
                systemModel = analyzer.analyze(systemModel, AnalyzerConfiguration.class.cast(config.createConfiguration(AnalyzerConfiguration.class)));
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
        return gen.generate(GeneratorConfiguration.class.cast(this.config.createConfiguration(GeneratorConfiguration.class)), graph);
    }

    @Override
    void executeRunner(String graphVisStr) {
        IRunner runner = new GraphVizRunner(RunnerConfiguration.class.cast(this.config.createConfiguration(RunnerConfiguration.class)));
        try {
            runner.execute(graphVisStr);
        } catch (Exception e) {
            throw new RuntimeException("[ INFO ]: Ensure that GraphViz bin folder is set in the environment variable.",
                    e);
        }
    }
}
