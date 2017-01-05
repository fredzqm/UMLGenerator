package generator.relParser;

/**
 * Abstract Class Representing a Relation.
 *
 * @author zhang, lamd
 */
public abstract class Relation implements IRelation, Comparable<IRelation> {
    private final String from;
    private final String to;

    private boolean isBijective;
    private int toCardinality;
    private int fromCardinality;

    public Relation(String from, String to) {
        this.to = to;
        this.from = from;
        this.isBijective = false;
        this.toCardinality = 0;
        this.fromCardinality = 0;
    }

    private static String getKey(IRelation x) {
        String fromName = x.getFrom();
        String toName = x.getTo();
        if (fromName.compareTo(toName) < 0) {
            return fromName + toName;
        }
        return toName + fromName;
    }

    public void setBijective(boolean status) {
        this.isBijective = status;
    }

    public void setCardinalityTo(int count) {
        this.toCardinality = count;
    }

    public void setCardinalityFrom(int count) {
        this.fromCardinality = count;
    }

    @Override
    public int getCardinalityTo() {
        return this.toCardinality;
    }

    @Override
    public int getCardinalityFrom() {
        return this.fromCardinality;
    }

    @Override
    public String getFrom() {
        return this.from;
    }

    @Override
    public String getTo() {
        return this.to;
    }

    @Override
    public boolean isBijective() {
        return this.isBijective;
    }

    @Override
    public int compareTo(IRelation o) {
        return getKey(this).compareTo(getKey(o));
    }

}
