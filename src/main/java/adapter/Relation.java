package adapter;

import analyzer.utility.ClassPair;
import generator.IEdge;

class Relation implements IEdge {
    private final ClassPair classPair;
    private final String edgeStyle;

    /**
     * Set the Relation's ClassPair and IRelationInfo
     *
     * @param classPair
     *            ClassPair to be set.
     * @param info
     *            IRelationInfo to be set.
     */
    public Relation(ClassPair classPair, String edgeStyle) {
        this.classPair = classPair;
        this.edgeStyle = edgeStyle;
    }

    /**
     * Returns the class pair of the Relation.
     *
     * @return ClassPair of the Relation.
     */
    public ClassPair getClassPair() {
        return classPair;
    }

    /**
     * Returns the name of the class that it points from.
     *
     * @return String of the Class name this relationship is pointing from.
     */
    public String getFrom() {
        return this.classPair.getFrom().getName();
    }

    /**
     * Returns the name of the class it is pointing to.
     *
     * @return String of the Class name this relationship is pointing to.
     */
    public String getTo() {
        return this.classPair.getTo().getName();
    }

    @Override
    public String toString() {
        return String.format("%s -> %s:\t\t%s\n", classPair.getFrom(), classPair.getTo(), edgeStyle);
    }

    @Override
    public String getEdgeStyle() {
        return edgeStyle;
    }
}
