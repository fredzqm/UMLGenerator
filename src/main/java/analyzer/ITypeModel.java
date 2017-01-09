package analyzer;

public interface ITypeModel {

	/**
	 * @return all the classes that this type depends on
	 */
	Iterable<? extends IClassModel> getTypeDependsOn();

	IClassModel getClassModel();

	int getDimension();

	default ITypeModel getGenericArg(int index) {
		return null;
	}

	default int getGenericArgLength() {
		return 0;
	}

	ITypeModel assignTo(String string);
}
