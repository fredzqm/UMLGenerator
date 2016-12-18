package generator;

import generator.parser.*;
import utility.IFilter;
import utility.Modifier;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by lamd on 12/17/2016.
 */
public class GraphVizParseGuideFactory implements IParseGuideFactory {
    private final IFilter<Modifier> filters;

    GraphVizParseGuideFactory(IFilter<Modifier> filters) {
        this.filters = filters;
    }

    @Override
    public IParseGuide<IClassModel> createClassParser() {
        IParser<IClassModel> classParser = new GraphVizClassParser(this.filters, (data) -> true, (method) -> true);
        return new GraphVizParseGuide<>(classParser, null);
    }

    @Override
    public Collection<IParseGuide<IClassModel>> createRelationshipParsers() {
        Collection<IParseGuide<IClassModel>> relationshipParsers = new ArrayList<>();

        IParser<IClassModel> extendsRelParser = new GraphVizSuperClassRelParser(filters);
        IParser<IClassModel> implementsRelParser = new GraphVizInterfaceParser();
        IParser<IClassModel> hasRelPraser = new GraphVizHasRelParser(filters);
        IParser<IClassModel> dependsOnRelParser = new GraphVizDependsOnRelParser(filters);

        relationshipParsers.add(new GraphVizParseGuide<>(extendsRelParser, "edge [arrowhead=onormal]"));
        relationshipParsers.add(new GraphVizParseGuide<>(implementsRelParser, "edge [arrowhead=onormal, style=dashed]"));
        relationshipParsers.add(new GraphVizParseGuide<>(hasRelPraser, "edge [arrowhead=vee]"));
        relationshipParsers.add(new GraphVizParseGuide<>(dependsOnRelParser, "edge [arrowhead=vee style=dashed]"));

        return relationshipParsers;
    }
}
