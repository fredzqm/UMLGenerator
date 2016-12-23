package generator.relParser;

/**
 * A GraphVizParser for the model's interface.
 * <p>
 * Created by lamd on 12/14/2016.
 */
public class GraphVizInterfaceParser implements IParseGuide {
	
    @Override
    public String getEdgeStyle(IRelation edge) {
        return "arrowhead=onormal style=dashed ";
    }

}