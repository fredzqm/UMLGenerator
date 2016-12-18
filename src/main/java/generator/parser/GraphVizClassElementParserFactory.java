package generator.parser;

import generator.IClassModel;
import generator.IFieldModel;
import generator.IMethodModel;
import generator.IParser;
import utility.IFilter;
import utility.Modifier;

/**
 * A ClassElementParserFactory for GraphViz.
 * <p>
 * Created by lamd on 12/18/2016.
 */
public class GraphVizClassElementParserFactory implements IClassElementParserFactory {

    GraphVizClassElementParserFactory() {
        // TODO: Figure out what else might change with this factory. At the moment, we have separated the creation (what changes) from the formatting (what stays the same).
    }

    @Override
    public IParser<IClassModel> createHeaderParser() {
        return new GraphVizHeaderParser(new GraphVizClassTypeParser());
    }

    @Override
    public IParser<IFieldModel> createFieldParser(IFilter<Modifier> filters) {
        return new GraphVizFieldParser(filters);
    }

    @Override
    public IParser<IMethodModel> createMethodParser(IFilter<Modifier> filters) {
        return new GraphVizMethodParser(filters);
    }
}
