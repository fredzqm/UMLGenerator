package generator;

import generator.parser.*;
import generator.parser.GraphVizClassParser;
import utility.IFilter;
import utility.Modifier;

import java.util.ArrayList;
import java.util.Collection;

/**
 * An Implementation of the default UML GraphViz ParseGuide Factory.
 * <p>
 * Created by lamd on 12/17/2016.
 */
class GraphVizParseGuideFactory implements IParseGuideFactory {
	private final IFilter<Modifier> filters;

	/**
	 * Contructs a GraphVizParseGuideFactory.
	 *
	 * @param filters
	 *            Modifier Filters.
	 */
	GraphVizParseGuideFactory(IFilter<Modifier> filters) {
		this.filters = filters;
	}

	@Override
	public IParser<IClassModel> createClassParser() {
		return new GraphVizClassParser(this.filters, (data) -> true, (method) -> true);
	}

	@Override
	public Collection<IParseGuide> createRelationshipParsers() {
		Collection<IParseGuide> relationshipParsers = new ArrayList<>();

		IParser<IClassModel> extendsRelParser = new GraphVizSuperClassRelParser(filters);
		IParser<IClassModel> implementsRelParser = new GraphVizInterfaceParser();
		IParser<IClassModel> hasRelPraser = new GraphVizHasRelParser(filters);
		IParser<IClassModel> dependsOnRelParser = new GraphVizDependsOnRelParser(filters);

		relationshipParsers.add(new GraphVizParseGuide(extendsRelParser, "edge [arrowhead=onormal]"));
		relationshipParsers.add(new GraphVizParseGuide(implementsRelParser, "edge [arrowhead=onormal, style=dashed]"));
		relationshipParsers.add(new GraphVizParseGuide(hasRelPraser, "edge [arrowhead=vee]"));
		relationshipParsers.add(new GraphVizParseGuide(dependsOnRelParser, "edge [arrowhead=vee style=dashed]"));

		return relationshipParsers;
	}
}
