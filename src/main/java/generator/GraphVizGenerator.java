package generator;

/**
 * A GraphVizGenerator that outputs DOT files for GraphViz.
 * <p>
 * Created by lamd on 12/7/2016.
 */
public class GraphVizGenerator extends AbstractGenerator implements IGenerator {
    public GraphVizGenerator(IGeneratorConfiguration config) {
        super(config);
    }

    @Override
    void createParsers() {
        // FIXME: Figure out how to get ride of warnings.
        this.parsers.add(this.factory.createClassParser());
        this.parsers.addAll(this.factory.createRelationshipParsers());
    }
}