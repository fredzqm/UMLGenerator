package analyzer;

import generator.IEdge;

public class Relation implements IEdge {
    private final ClassPair classPair;
    private final IRelationInfo info;
    
    /**
     * Set the Relation's ClassPair and IRelationInfo
     *
     * @param classPair
     *            ClassPair to be set.
     * @param info
     *            IRelationInfo to be set.
     */
    public Relation(ClassPair classPair, IRelationInfo info) {
        this.classPair = classPair;
        this.info = info;
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
    
    /**
     * Returns the Relationship's Info.
     *
     * @return Relationship's Info.
     */
    public IRelationInfo getInfo() {
        return this.info;
    }
    
    @Override
    public String toString() {
        return String.format("%s -> %s:\t\t%s\n", classPair.getFrom(), classPair.getTo(), this.info.toString());
    }
    
    @Override
    public String getEdgeStyle() {
        return this.info.getEdgeStyle();
    }
}
