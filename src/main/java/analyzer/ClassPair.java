package analyzer;

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

    IClassModel getFrom() {
        return this.from;
    }

    public IClassModel getTo() {
        return this.to;
    }

    public ClassPair reverse() {
        return new ClassPair(to, from);
    }

    public boolean isLoop() {
        return from.equals(to);
    }

    @Override
    public int hashCode() {
        return from.getUnderlyingClassModel().hashCode() + to.getUnderlyingClassModel().hashCode() * 127;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof ClassPair) {
            ClassPair rel = (ClassPair) obj;
            return to.getUnderlyingClassModel().equals(rel.to.getUnderlyingClassModel())
                    && from.getUnderlyingClassModel().equals(rel.from.getUnderlyingClassModel());
        }
        return false;
    }

    @Override
    public String toString() {
        return String.format("%s -> %s", from, to);
    }
}
