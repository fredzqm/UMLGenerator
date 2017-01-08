package analyzer;

public interface ITypeModel {

	/**
	 * @return all the classes that this type depends on
	 */
	Iterable<? extends IClassModel> getDependsOn();
}
