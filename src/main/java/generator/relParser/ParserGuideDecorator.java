package generator.relParser;

import generator.IClassModel;

public class ParserGuideDecorator implements IParseGuide {
	private IParseGuide decorated;
	private String addedEdgeStyle;
	
	public ParserGuideDecorator(IParseGuide decorated, String newEdgeStyle) {
		this.decorated = decorated;
		this.addedEdgeStyle = newEdgeStyle;
	}
	
	@Override
	public String parse(IClassModel data) {
		return decorated.parse(data);
	}

	@Override
	public String getEdgeStyle() {
		return decorated.getEdgeStyle() + addedEdgeStyle;
	}

}
