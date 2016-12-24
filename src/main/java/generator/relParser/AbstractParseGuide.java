package generator.relParser;

import java.util.HashMap;
import java.util.Map;

public abstract class AbstractParseGuide implements IParseGuide {
	private Map<Class<? extends IRelation>, IParseGuide> map = new HashMap<>();

	public AbstractParseGuide() {
		map = new HashMap<>();
		initializeMap();
	}

	protected void map(Class<? extends IRelation> relClass, IParseGuide guide) {
		map.put(relClass, guide);
	}

	public abstract void initializeMap();

	@Override
	public String getEdgeStyle(IRelation edge) {
		Class<? extends IRelation> edgeClass = edge.getClass();
		if (map.containsKey(edgeClass)) {
			return map.get(edgeClass).getEdgeStyle(edge);
		}
		return "";
	}

}
