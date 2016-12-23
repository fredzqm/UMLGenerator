package analyzer;

import generator.ISystemModel;

/**
 * An Interface for an Analyzer.
 * <p>
 * Created by lamd on 12/7/2016.
 */
public interface IAnalyzer {
    ISystemModel analyze(ISystemModel sm);
}
