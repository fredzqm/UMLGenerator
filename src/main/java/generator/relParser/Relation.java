package generator.relParser;

import generator.classParser.IClassModel;

/**
 * 
 * Relation represet
 * 
 * @author zhang
 *
 */
public abstract class Relation implements Comparable<Relation> {
    private final IClassModel from;
    private final IClassModel to;
    private final String key;

    public Relation(IClassModel from, IClassModel to) {
        this.to = to;
        this.from = from;
        this.key = getKey();
    }

    public String getFromName() {
        return this.from.getName();
    }

    public String getToName() {
        return this.to.getName();
    }

    public IClassModel getFrom() {
        return this.from;
    }

    public IClassModel getTo() {
        return this.to;
    }

    private String getKey() {
        String fromName = this.getFromName();
        String toName = this.getToName();
        if (fromName.compareTo(toName) < 0)
            return fromName + toName;
        else
            return toName + fromName;
    }

    @Override
    public int compareTo(Relation o) {
        return this.key.compareTo(o.getKey());
    }

}
