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

	public IClassModel getFrom() {
		return this.from;
	}

	public IClassModel getTo() {
		return this.to;
	}

	public ClassPair reverse() {
		return new ClassPair(to, from);
	}

	@Override
	public int hashCode() {
		return this.from.hashCode() + this.to.hashCode() * 127;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof ClassPair) {
			ClassPair rel = (ClassPair) obj;
			return from.equals(rel.from) && to.equals(rel.to);
		}
		return false;
	}

	public boolean isLoop() {
		return from == to;
	}
}
