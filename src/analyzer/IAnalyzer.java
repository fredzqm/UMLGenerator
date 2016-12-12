package analyzer;

import generator.ISystemModel;

import java.util.Collection;

/**
 * An Interface for an Analyzer.
 * <p>
 * Created by lamd on 12/7/2016.
 */
public interface IAnalyzer {
    Collection<IPattern> analyze(ISystemModel sm);
}
