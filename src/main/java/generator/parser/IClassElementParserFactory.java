package generator.parser;

import generator.IClassModel;
import generator.IFieldModel;
import generator.IMethodModel;
import generator.IParser;
import utility.IFilter;
import utility.Modifier;

/**
 * An Interface for ClassElementParserFactory.
 * <p>
 * Created by lamd on 12/18/2016.
 */
public interface IClassElementParserFactory {
    /**
     * Returns a Header Parser.
     *
     * @return Header Parser.
     */
    IParser<IClassModel> createHeaderParser();

    /**
     * Returns a Field Parser.
     *
     * @param filters Filters for Parser.
     * @return Field Parser.
     */
    IParser<IFieldModel> createFieldParser(IFilter<Modifier> filters);

    /**
     * Returns a Method Parser.
     *
     * @param filters Filters for Parser.
     * @return Method Parser.
     */
    IParser<IMethodModel> createMethodParser(IFilter<Modifier> filters);
}
