package analyzer;

/**
 * An Interface for an Analyzer.
 * <p>
 * Created by lamd on 12/7/2016.
 */
public interface IAnalyzer {
	/**
	 * 
	 * @param systemModel
	 * @return
	 */
	ISystemModel analyze(ISystemModel systemModel, IAnalyzerConfiguraton config);
}
