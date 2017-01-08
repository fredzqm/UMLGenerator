package analyzerClassParser;

import analyzer.IClassModel;
import analyzer.IFieldModel;
import analyzer.IMethodModel;
import utility.IFilter;
import utility.Modifier;

/**
 * 
 * @author zhang
 *
 */
public interface IClassParserConfiguration {
	
	/**
	 * Return the set of Method Access Filters.
	 *
	 * @return Set of Method Access Filters.
	 */
	IFilter<Modifier> getModifierFilters();

	/**
	 * 
	 * @return
	 */
	Class<? extends IParser<IClassModel>> getHeaderParser();

	/**
	 * 
	 * @return
	 */
	Class<? extends IParser<IFieldModel>> getFieldParser();

	/**
	 * 
	 * @return
	 */
	Class<? extends IParser<IMethodModel>> getMethodParser();
}
