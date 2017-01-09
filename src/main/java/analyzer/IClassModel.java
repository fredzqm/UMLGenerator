package analyzer;

import java.util.List;

import generator.IVertex;
import utility.Modifier;

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
     * Returns the modifer fo the Class Model.
     *
     * @return the Modifier of the Class Model.
     */
    Modifier getModifier();

    /**
     * Returns the Stereotypes of the Class Model.
     *
     * @return the list of stereotypes name for this class
     */
    List<String> getStereoTypes();

    /**
     * Returns the super class's model of the Class Model.
     *
     * @return Class Model of the super class.
     */
    IClassModel getSuperClass();

    /**
     * Returns an Iterable of interfaces of the Class Model.
     *
     * @return Iterable of Interface Class Models.
     */
    Iterable<? extends IClassModel> getInterfaces();

    /**
     * Returns the Iterable of Class Model that is in a depends-on relation with this class model.
     *
     * @return Iterable of Class Model in a depends-on relation with.
     */
	Iterable<? extends IClassModel> getClassDependsOn();

}
