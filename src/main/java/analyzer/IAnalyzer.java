package main.java.analyzer;

/**
 * An Interface for an Analyzer.
 * <p>
 * Created by lamd on 12/7/2016.
 */
public interface IAnalyzer {
	Iterable<IPattern> analyze(IAnalyzerSystemModel sm);
}
