package generator;

/**
 * A GraphVizGenerator that outputs DOT files for GraphViz. It converts a graph
 * into a dot file
 * <p>
 * Created by lamd on 12/7/2016.
 */
public class GraphVizGenerator implements IGenerator {
    @Override
    public String generate(IGeneratorConfiguration config, IGraph graph) {
        // DOT parent.
        StringBuilder dotString = new StringBuilder();

        // Basic Configurations.
        dotString.append(String.format("\tnodesep=%s;\n\t%s;\n\trankdir=%s;\n\n", config.getNodeSep(),
                config.getNodeStyle(), config.getRankDir()));

        // Render the classes
        Iterable<? extends IVertex> vertices = graph.getVertices();
        vertices.forEach((vertex) -> {
            dotString.append(
                    String.format("\t\"%s\" [\n\t\tlabel = \"{%s}\"\n\t];\n", vertex.getName(), vertex.getLabel()));
        });

        // Parse each relationship.
        Iterable<? extends IEdge> edges = graph.getEdges();
        edges.forEach(edge -> {
            dotString.append(String.format("\t\"%s\" -> \"%s\" [%s];\n", edge.getFrom(), edge.getTo(),
                    edge.getEdgeStyle()));
        });

        return String.format("digraph GraphVizGeneratedDOT {\n%s}", dotString.toString());
    }
}