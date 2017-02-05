package adapter;

import analyzer.utility.IClassModel;
import generator.INode;

public class Node implements INode {
    private final IClassModel classModel;

    public Node(IClassModel classModel) {
        this.classModel = classModel;
    }

    @Override
    public String getName() {
        return classModel.getName();
    }

    @Override
    public String getLabel() {
        return classModel.getLabel();
    }

    @Override
    public String getNodeStyle() {
        return classModel.getNodeStyle();
    }

}
