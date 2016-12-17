package generator;

import generator.parser.*;
import utility.IFilter;
import utility.Modifier;

import java.util.ArrayList;
import java.util.Collection;

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
    public IParseGuide<IClassModel> createDependsOnParser() {
        IParser<IClassModel> dependsOnRelParser = new GraphVizDependsOnRelParser(filters);
        return new GraphVizParseGuide<>(dependsOnRelParser, "edge [arrowhead=vee style=dashed]");
    }

    @Override
    public Collection<IParseGuide<IClassModel>> createCustomParser() {
        return new ArrayList<>(); // By default, GraphVizParseGuide does not have any custom parser.
    }

    @Override
    public IParseGuide createHasParser() {
        IParser<IClassModel> hasRelPraser = new GraphVizHasRelParser(filters);
        return new GraphVizParseGuide<>(hasRelPraser, "edge [arrowhead=vee]");
    }

    @Override
    public IParseGuide createSuperClassParser() {
        IParser<IClassModel> extendsRelParser = new GraphVizSuperClassRelParser(filters);
        return new GraphVizParseGuide<>(extendsRelParser, "edge [arrowhead=onormal]");
    }

    @Override
    public IParseGuide<IClassModel> createInterfaceParser() {
        IParser<IClassModel> implementsRelParser = new GraphVizInterfaceParser();
        return new GraphVizParseGuide<>(implementsRelParser, "edge [arrowhead=onormal, style=dashed]");
    }
}
