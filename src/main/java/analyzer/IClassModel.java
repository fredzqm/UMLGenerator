package analyzer;

import utility.Modifier;

import java.util.List;
import java.util.Map;

import generator.IVertex;

/**
 * An interface for Class Models.
 * <p>
 * Created by lamd on 12/9/2016.
 */
public interface IClassModel extends IVertex {

	/**
	 * Returns the String of the Model's Class Name.
	 *
	 * @return Class Name.
	 */
	String getName();

	/**
	 * Returns an Iterable of the Model's Fields. Excluding those inherited
	 *
	 * @return Fields of the Model.
	 */
	Iterable<? extends IFieldModel> getFields();

	/**
	 * Returns an Iterable of the Model's Methods. Excluding those inherited
	 *
	 * @return Methods of the Model.
	 */
	Iterable<? extends IMethodModel> getMethods();

	/**
	 * @return the Modifier of the Class Model.
	 */
	Modifier getModifier();

	/**
	 * @return the list of stereotypes name for this class
	 */
	List<String> getStereoTypes();

	IClassModel getSuperClass();

	Iterable<? extends IClassModel> getInterfaces();

	Map<? extends IClassModel, Integer> getHasRelation();

	Iterable<? extends IClassModel> getDependsRelation();

}