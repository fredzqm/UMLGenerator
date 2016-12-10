package models;

import java.util.List;

/**
 * Created by lamd on 12/9/2016.
 */
public interface IClassModel {
    /**
     * Returns the String of the Model's Class Name.
     *
     * @return Class Name.
     */
    String getClassName();

    /**
     * Returns the Model's ClassType enum.
     *
     * @return ClassType
     */
    ClassType getType();

    /**
     * Returns the List of the Model's Fields.
     *
     * @return Fields of the Model.
     */
    Iterable<IFieldModel> getFields();

    /**
     * Returns the List of the Model's Methods.
     *
     * @return Methods of the Model.
     */
    List<IMethodModel> getMethods();

    /**
     * Returns the String of the Model's superclass.
     *
     * @return Model's superclass.
     */
    String getSuperClass();

    /**
     * Returns the List of classes the Model inherits from.
     *
     * @return Intefaces the model inherits.
     */
    List<IClassModel> getInterfaces();

    /**
     * Returns the List of the Model's Has-A relation.
     *
     * @return List of Classes with a Has-A relationship with the Model.
     */
    List<IClassModel> getHasRelation();

    /**
     * Returns the List of the Model's Depends-On Relation.
     *
     * @return List of Classes with a Depends-On relationship with the Model.
     */
    List<IClassModel> getDependsRelation();


}
