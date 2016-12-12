package analyzer;

/**
 * Created by lamd on 12/7/2016.
 */
public interface IAnalyzer {
	Iterable<IPattern> analyze(ISystemModel sm);
}
