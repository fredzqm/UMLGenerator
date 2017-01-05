package generator.relParser;

public final class Relation {
    private final ClassPair classPair;
    private final IRelationInfo info;

    public Relation(ClassPair classPair, IRelationInfo info) {
        this.classPair = classPair;
        this.info = info;
    }

    public ClassPair getClassPair() {
        return classPair;
    }

    /**
     * @return the name of class points to
     */
    public String getFrom() {
        return classPair.getFrom();
    }

    /**
     * @return the name of class from
     */
    public String getTo() {
        return classPair.getTo();
    }

    /**
     * @return
     */
    public IRelationInfo getInfo() {
        return info;
    }

}
