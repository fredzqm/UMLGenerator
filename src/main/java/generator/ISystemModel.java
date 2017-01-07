package generator;

public interface ISystemModel {
	/**
	 * Returns an Iterable of Class Models contained within the System Model.
	 *
	 * @return Iterable of Class Models.
	 */
	Iterable<? extends IVertex> getClasses();

	/**
	 * Returns an Iterable of Relations contained within the SystemModel.
	 *
	 * @return Iterable of Relations contained within the SystemModel.
	 */
	Iterable<? extends IEdge> getRelations();
}
