package generator.relationshipParser;

import java.util.HashMap;
import java.util.Map;

public abstract class AbstractParseGuide implements IParseGuide {
    private Map<Class<?>, IParseGuide> parseMap = new HashMap<>();

    AbstractParseGuide() {
        this.parseMap = new HashMap<>();
        initializeMap();
    }

    protected void map(Class<?> relClass, IParseGuide guide) {
        this.parseMap.put(relClass, guide);
    }

    /**
     * Initializes the ParseMap.
     *
     */
    public abstract void initializeMap();

    @Override
    public String getEdgeStyle(IRelationInfo edge) {
        Class<?> edgeClass = edge.getClass();
        if (parseMap.containsKey(edgeClass)) {
            return parseMap.get(edgeClass).getEdgeStyle(edge);
        }
        return "";
    }
}
