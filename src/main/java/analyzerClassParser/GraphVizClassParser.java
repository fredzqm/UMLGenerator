package analyzerClassParser;

import analyzer.IClassModel;
import analyzer.IFieldModel;
import analyzer.IMethodModel;
import utility.IFilter;
import utility.Modifier;

/**
 * Representing a single class in the DOT language.
 */
public class GraphVizClassParser extends AbstractClassParser {

    /**
     * Constructs a GraphVizClassParser.
     *
     * @param config ClassParser Configuration.
     */
    GraphVizClassParser(IClassParserConfiguration config) {
        super(config);
    }

    @Override
    public IFilter<IMethodModel> createFieldMethodFilter(IClassParserConfiguration config) {
        IFilter<Modifier> modifierFilter = config.getModifierFilters();
        return (m) -> modifierFilter.filter(m.getModifier());
    }

    @Override
    public IParser<IMethodModel> createMethodParser(IClassParserConfiguration config) {
        return new GraphVizMethodParser();
    }

    @Override
    public IFilter<IFieldModel> createFieldFilter(IClassParserConfiguration config) {
        IFilter<Modifier> modifierFilter = config.getModifierFilters();
        return (f) -> modifierFilter.filter(f.getModifier());
    }

    @Override
    public IParser<IFieldModel> createFieldParser(IClassParserConfiguration config) {
        return new GraphVizFieldParser();
    }

    @Override
    public IParser<IClassModel> createHeaderParser(IClassParserConfiguration config) {
        return new GraphVizHeaderParser();
    }

}
