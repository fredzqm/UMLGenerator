package analyzerClassParser;

import analyzer.IClassModel;
import analyzer.IFieldModel;
import analyzer.IMethodModel;
import analyzer.ITypeModel;
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
    IParser<IClassModel> getHeaderParser();
    
    /**
     * 
     * @return class as the field parser
     */
    IParser<IFieldModel> getFieldParser();
    
    /**
     * 
     * @return class as the method parser
     */
    IParser<IMethodModel> getMethodParser();
    
    /**
     * 
     * @return class as the type parser
     */
    IParser<ITypeModel> getTypeParser();
    
    /**
     * 
     * @return
     */
    IParser<Modifier> getModifierParser();
}
