package generator;

public interface IGraph {
	/**
	 * Returns an Iterable of Class Models contained within the System Model.
	 *
	 * @return Iterable of Class Models.
	 */
	Iterable<? extends IVertex> getVertice();

	/**
	 * Returns an Iterable of Relations contained within the SystemModel.
	 *
	 * @return Iterable of Relations contained within the SystemModel.
	 */
	Iterable<? extends IEdge> getEdges();
}
