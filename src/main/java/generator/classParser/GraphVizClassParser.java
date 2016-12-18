package generator.classParser;

import generator.*;
import utility.IFilter;
import utility.Modifier;

/**
 * Representing a single class in the DOT language.
 */
public class GraphVizClassParser extends AbstractClassParser {

	public GraphVizClassParser(IFilter<Modifier> filters, IFilter<IFieldModel> fieldFilters,
			IFilter<IMethodModel> methodFilters) {
		super(filters, fieldFilters, methodFilters);
	}

	@Override
	public IParser<IMethodModel> createMethodParser(IFilter<Modifier> filters) {
		return new GraphVizMethodParser(filters);
	}

	@Override
	public IParser<IFieldModel> createFieldParser(IFilter<Modifier> filters) {
		return new GraphVizFieldParser(filters);
	}

	@Override
	public IParser<IClassModel> createHeaderParser() {
		return new GraphVizHeaderParser(new GraphVizClassTypeParser());
	}



}
