package analyzer;

/**
 * An Interface for an Analyzer.
 * <p>
 * Created by lamd on 12/7/2016.
 */
public interface IAnalyzer {
	/**
	 * 
	 * @param sm
	 * @return
	 */
	IASystemModel analyze(IASystemModel sm, IAnalyzerConfiguraton config);
}
