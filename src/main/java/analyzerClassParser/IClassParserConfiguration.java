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
	 * @return class as parser for the header
	 */
	Class<? extends IParser<IClassModel>> getHeaderParser();

	/**
	 * 
	 * @return class as the field parser
	 */
	Class<? extends IParser<IFieldModel>> getFieldParser();

	/**
	 * 
	 * @return class as the method parser
	 */
	Class<? extends IParser<IMethodModel>> getMethodParser();
}
