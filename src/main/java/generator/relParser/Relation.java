package generator.relParser;

/**
 * Relation represet
 *
 * @author zhang
 */
public abstract class Relation implements IRelation {
    private final String from;
    private final String to;

    public Relation(String from, String to) {
        this.to = to;
        this.from = from;
    }

    private static String getKey(IRelation x) {
        String fromName = x.getFrom();
        String toName = x.getTo();
        if (fromName.compareTo(toName) < 0)
            return fromName + toName;
        else
            return toName + fromName;
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
    public int compareTo(IRelation o) {
        return getKey(this).compareTo(getKey(o));
    }

}
