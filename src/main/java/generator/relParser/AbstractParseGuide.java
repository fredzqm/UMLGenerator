package generator.relParser;

import java.util.HashMap;
import java.util.Map;

public abstract class AbstractParseGuide implements IParseGuide {
	private Map<Class<?>, IParseGuide> map = new HashMap<>();

	public AbstractParseGuide() {
		map = new HashMap<>();
		initializeMap();
	}

	protected void map(Class<?> relClass, IParseGuide guide) {
		map.put(relClass, guide);
	}

	public abstract void initializeMap();

	@Override
	public String getEdgeStyle(IRelationInfo edge) {
		Class<?> edgeClass = edge.getClass();
		if (map.containsKey(edgeClass)) {
			return map.get(edgeClass).getEdgeStyle(edge);
		}
		return "";
	}

}
