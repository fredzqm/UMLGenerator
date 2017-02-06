package analyzer.utility;

import config.IConfiguration;

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
     * @param config      IConfiguration object that the analyzer can retrieves
     *                    configurations from
     */
    void analyze(ISystemModel systemModel, IConfiguration config);
}
