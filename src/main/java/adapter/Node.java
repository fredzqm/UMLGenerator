package adapter;

import generator.INode;

public class Node implements INode {
    private String name;
    private String label;
    private String nodeStyle;

    public Node(String name, String label, String nodeStyle) {
        this.name = name;
        this.label = label;
        this.nodeStyle = nodeStyle;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getLabel() {
        return label;
    }

    @Override
    public String getNodeStyle() {
        return nodeStyle;
    }

}
