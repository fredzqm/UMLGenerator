package generator.classParser;

import generator.IGeneratorConfiguration;
import utility.IFilter;
import utility.Modifier;

/**
 * Representing a single class in the DOT language.
 */
public class GraphVizClassParser extends AbstractClassParser {

    public GraphVizClassParser(IGeneratorConfiguration config) {
        super(config);
    }

    @Override
    public IFilter<IMethodModel> createFieldMethodFilter(IGeneratorConfiguration config) {
        IFilter<Modifier> modifierFilter = config.getModifierFilters();
        return (m) -> modifierFilter.filter(m.getModifier());
    }

    @Override
    public IParser<IMethodModel> createMethodParser(IGeneratorConfiguration config) {
        return new GraphVizMethodParser();
    }

    @Override
    public IFilter<IFieldModel> createFieldFilter(IGeneratorConfiguration config) {
        IFilter<Modifier> modifierFilter = config.getModifierFilters();
        return (f) -> modifierFilter.filter(f.getModifier());
    }

    @Override
    public IParser<IFieldModel> createFieldParser(IGeneratorConfiguration config) {
        return new GraphVizFieldParser();
    }

    @Override
    public IParser<IClassModel> createHeaderParser(IGeneratorConfiguration config) {
        return new GraphVizHeaderParser();
    }

}
