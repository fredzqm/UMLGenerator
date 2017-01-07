package analyzer;

public interface IAnalyzerConfiguraton {

	/**
	 * 
	 * @param analyzerClass
	 * @return the specific configuration object for this analyzer
	 */
	Object getConfigurationFor(Class<? extends IAnalyzer> analyzerClass);
}
