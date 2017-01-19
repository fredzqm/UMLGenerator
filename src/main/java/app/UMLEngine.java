package app;

import analyzer.utility.IAnalyzer;
import analyzer.utility.ISystemModel;
import config.*;
import generator.IGenerator;
import generator.IGeneratorConfiguration;
import generator.IGraph;
import model.IModelConfiguration;
import model.SystemModel;
import runner.GraphVizRunner;
import runner.IRunner;
import runner.IRunnerConfiguration;

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

    public static UMLEngine getInstance(IConfiguration config2) {
        EngineConfiguration engineConfig = config2.createConfiguration(EngineConfiguration.class);
        return getInstance(engineConfig);
    }

    @Override
    public ISystemModel createSystemModel() {
        IModelConfiguration modelConf = config.getModelConfiguration();
        return SystemModel.getInstance(modelConf);
    }

    @Override
    ISystemModel analyze(ISystemModel systemModel) {
        Iterable<Class<? extends IAnalyzer>> anClassLs = this.config.getAnalyzers();
        for (Class<? extends IAnalyzer> anClass : anClassLs) {
            try {
                IAnalyzer analyzer = anClass.newInstance();
                systemModel = analyzer.analyze(systemModel, config.getConfigurationFor(anClass));
            } catch (InstantiationException | IllegalAccessException e) {
                throw new RuntimeException("Analyzer " + anClass + " does not have an empty constructor", e);
            }
        }
        return systemModel;
    }

    @Override
    String generate(IGraph graph) {
        IGeneratorConfiguration generatorConf = config.getGeneratorConfiguration();
        Class<? extends IGenerator> genClass = config.getGenerator();
        IGenerator gen;
        try {
            gen = genClass.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            throw new RuntimeException("Generator " + genClass + " does not have an empty constructor", e);
        }
        return gen.generate(generatorConf, graph);
    }

    @Override
    void executeRunner(String graphVisStr) {
        IRunnerConfiguration runnerConf = config.getRunnerConfiguration();
        IRunner runner = new GraphVizRunner(runnerConf);
        try {
            runner.execute(graphVisStr);
        } catch (Exception e) {
            throw new RuntimeException("[ INFO ]: Ensure that GraphViz bin folder is set in the environment variable.",
                    e);
        }
    }

}
