package analyzer.utility;

/**
 * An Interface for an Analyzer.
 * <p>
 * Created by lamd on 12/7/2016.
 */
public interface IAnalyzer {
    /**
     * Returns an Analyzed System Model.
     *
     * @param systemModel System Model to be analyzed.
     * @param config      Analzyer Configuration.
     * @return Analyzed System Model.
     */
    ISystemModel analyze(ISystemModel systemModel, IAnalyzerConfiguration config);
}
