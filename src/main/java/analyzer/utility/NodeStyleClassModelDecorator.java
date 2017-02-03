package analyzer.utility;

/**
 * Created by lamd on 1/15/2017.
 */
public class NodeStyleClassModelDecorator extends IClassModelFilter {
    private final String nodeStyle;

    public NodeStyleClassModelDecorator(IClassModel classModel, String nodeStyle) {
        super(classModel);
        this.nodeStyle = nodeStyle;
    }

    @Override
    public String getNodeStyle() {
        return super.getNodeStyle() + " " + nodeStyle;
    }

}
