package analyzer;

/**
 * This is an immutable class representing a pair of class.
 *
 * @author zhang
 */
public final class ClassPair {
    private final IClassModel from;
    private final IClassModel underLyingfrom;
    private final IClassModel to;
    private final IClassModel underLyingto;

    private IClassModel getUnderlyingClassModel(IClassModel x) {
        while (x instanceof IClassModelFilter) {
            x = ((IClassModelFilter) x).getClassModel();
        }
        return x;
    }

    public ClassPair(IClassModel from, IClassModel to) {
        this.to = to;
        this.from = from;
        this.underLyingto = getUnderlyingClassModel(to);
        this.underLyingfrom = getUnderlyingClassModel(from);
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
        return underLyingfrom.hashCode() + underLyingto.hashCode() * 127;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof ClassPair) {
            ClassPair rel = (ClassPair) obj;
            return rel.underLyingfrom.equals(rel.underLyingfrom) && underLyingto.equals(rel.underLyingto);
        }
        return false;
    }

    @Override
    public String toString() {
        return String.format("%s -> %s", from, to);
    }
}
