package analyzer;

import generator.IVertex;
import utility.ClassType;

import java.util.Collections;
import java.util.List;

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
     * @return the type of the class
     */
    ClassType getType();

    /**
     * @return true if this class is a final class
     */
    boolean isFinal();

    /**
     * @return true if this class is static
     */
    boolean isStatic();

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
     * Returns the Stereotypes of the Class Model.
     *
     * @return the list of stereotypes name for this class
     */
    default List<String> getStereoTypes() {
        return Collections.emptyList();
    }

    /**
     * @return the label for this vertex
     */
    default String getLabel() {
        return "";
    }

}
