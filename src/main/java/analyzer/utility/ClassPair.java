package analyzer.utility;

/**
 * This is an immutable class representing a pair of class.
 *
 * @author zhang
 */
public final class ClassPair {
    private final IClassModel from;
    private final IClassModel to;

    public ClassPair(IClassModel from, IClassModel to) {
        this.to = to;
        this.from = from;
    }

    public IClassModel getFrom() {
        return this.from;
    }

    public IClassModel getTo() {
        return this.to;
    }

    public ClassPair reverse() {
        return new ClassPair(this.to, this.from);
    }

    public boolean isLoop() {
        return this.from.equals(this.to);
    }

    @Override
    public int hashCode() {
        return this.from.hashCode() + this.to.hashCode() * 127;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof ClassPair) {
            ClassPair rel = ClassPair.class.cast(obj);
            return this.to.equals(rel.to) && this.from.equals(rel.from);
        }
        return false;
    }

    @Override
    public String toString() {
        return String.format("%s -> %s", this.from, this.to);
    }
}
