package analyzer;

import generator.IFormat;

public class Format implements IFormat {
    @Override
    public double getNodeSep() {
        return 0;
    }

    @Override
    public String getRankDir() {
        return null;
    }

    @Override
    public String superclassEdgeStyle() {
        return null;
    }

    @Override
    public String getNodeStyle() {
        return null;
    }

    @Override
    public String getInheritanceEdgeStyle() {
        return null;
    }

    @Override
    public String getHasAEdgeStyle() {
        return null;
    }

    @Override
    public String getDependOnEdgeStyle() {
        return null;
    }
}
