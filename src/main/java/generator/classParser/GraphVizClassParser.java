package generator.classParser;

import generator.*;
import utility.IFilter;

/**
 * Representing a single class in the DOT language.
 */
public class GraphVizClassParser extends AbstractClassParser {

	public GraphVizClassParser(IGeneratorConfiguration config) {
		super(config);
	}

	@Override
	public IFilter<IMethodModel> createFieldMethodFilter(IGeneratorConfiguration config) {
		return (m) -> true;
	}

	@Override
	public IParser<IMethodModel> createMethodParser(IGeneratorConfiguration config) {
		return new GraphVizMethodParser(config.getModifierFilters());
	}

	@Override
	public IFilter<IFieldModel> createFieldFilter(IGeneratorConfiguration config) {
		return (f) -> true;
	}

	@Override
	public IParser<IFieldModel> createFieldParser(IGeneratorConfiguration config) {
		return new GraphVizFieldParser(config.getModifierFilters());
	}

	@Override
	public IParser<IClassModel> createHeaderParser(IGeneratorConfiguration config) {
		return new GraphVizHeaderParser(new GraphVizClassTypeParser());
	}

}
