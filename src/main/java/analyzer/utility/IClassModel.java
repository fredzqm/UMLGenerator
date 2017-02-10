package analyzer.utility;

import utility.ClassType;

import java.util.Collection;

/**
 * An interface for Class Models.
 * <p>
 * Created by lamd on 12/9/2016.
 */
public interface IClassModel {
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
     * @return true if this class is synthetic
     */
    boolean isSynthetic();

    /**
     * Returns the super class's model of the Class Model. {@Code} Null if the
     * class itself is java.lang.Object
     *
     * @return Class Model of the super class.
     */
    IClassModel getSuperClass();

    /**
     * Returns an Iterable of interfaces of the Class Model.
     *
     * @return Iterable of Interface Class Models.
     */
    Collection<? extends IClassModel> getInterfaces();

    /**
     * Returns an Iterable of the Model's Fields. Excluding those inherited
     *
     * @return Fields of the Model.
     */
    Collection<? extends IFieldModel> getFields();

    /**
     * @param superClazz
     * @return true if it is the sub class of superClazz
     */
    boolean isSubClazzOf(IClassModel superClazz);

    /**
     * This method is used to ensure that even if a classModel is decorated, it
     * still equals to the original classModel
     *
     * @return get the reference to the innest {@link ClassModel} that is //
     * FIXME Fred decorated
     */
    IClassModel getUnderlyingClassModel();

    /**
     * Returns an Iterable of the Model's Methods. Excluding those inherited
     *
     * @return Methods of the Model.
     */
    Collection<? extends IMethodModel> getMethods();

}
