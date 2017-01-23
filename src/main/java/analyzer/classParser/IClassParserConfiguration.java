package analyzer.classParser;

import analyzer.utility.IClassModel;
import analyzer.utility.IFieldModel;
import analyzer.utility.IMethodModel;
import analyzer.utility.ITypeModel;
import config.Configurable;
import utility.IFilter;
import utility.Modifier;

/**
 * @author zhang
 */
public interface IClassParserConfiguration extends Configurable {

    /**
     * Return the set of Method Access Filters.
     *
     * @return Set of Method Access Filters.
     */
    IFilter<Modifier> getModifierFilters();

    /**
     * @return class as parser for the header
     */
    IParser<IClassModel> getHeaderParser();

    /**
     * @return class as the field parser
     */
    IParser<IFieldModel> getFieldParser();

    /**
     * @return class as the method parser
     */
    IParser<IMethodModel> getMethodParser();

    /**
     * @return class as the type parser
     */
    IParser<ITypeModel> getTypeParser();

    /**
     * @return
     */
    IParser<Modifier> getModifierParser();
}
