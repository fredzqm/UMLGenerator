package generator.parser;

import com.sun.org.apache.xpath.internal.operations.Mod;
import generator.IClassModel;
import generator.IFieldModel;
import generator.IMethodModel;
import generator.IParser;
import utility.IFilter;
import utility.Modifier;

/**
 * Created by lamd on 12/18/2016.
 */
public interface IClassElementParserFactory {
    IParser<IClassModel> createHeaderParser();

    IParser<IFieldModel> createFieldParser(IFilter<Modifier> fieldFilters);

    IParser<IMethodModel> createMethodParser(IFilter<Modifier> methodFilters);
}
