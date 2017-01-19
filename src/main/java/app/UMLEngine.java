package app;

import java.util.List;

import analyzer.utility.IAnalyzer;
import analyzer.utility.ISystemModel;
import config.EngineConfiguration;
import config.IConfiguration;
import config.IEngineConfiguration;
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

    static UMLEngine getInstance(IConfiguration config) {
        return new UMLEngine(config.createConfiguration(EngineConfiguration.class));
    }

    @Override
    public ISystemModel createSystemModel() {
        return SystemModel.getInstance(config);
    }

    @Override
    ISystemModel analyze(ISystemModel systemModel) {
        List<IAnalyzer> anClassLs = this.config.getAnalyzers();
        for (IAnalyzer analyzer : anClassLs) {
            systemModel = analyzer.analyze(systemModel, config);
        }
        return systemModel;
    }

    @Override
    String generate(IGraph graph) {
        IGenerator gen = config.getGenerator();
        return gen.generate(graph, config);
    }

    @Override
    void executeRunner(String graphVisStr) {
        IRunner runner = GraphVizRunner.getInstance(config);
        try {
            runner.execute(graphVisStr);
        } catch (Exception e) {
            throw new RuntimeException("[ INFO ]: Ensure that GraphViz bin folder is set in the environment variable.",
                    e);
        }
    }

}
