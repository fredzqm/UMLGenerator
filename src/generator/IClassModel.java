package generator;

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
     * Returns the Model's ClassType.
     *
     * @return ClassType
     */
    IClassType getType();

    /**
     * Returns an Iterable of the Model's Fields.
     *
     * @return Fields of the Model.
     */
    Iterable<? extends IFieldModel> getFields();

    /**
     * Returns an Iterable of the Model's Methods.
     *
     * @return Methods of the Model.
     */
    Iterable<? extends IMethodModel> getMethods();

    /**
     * Returns the IClassModel of the Model's superclass.
     *
     * @return Model's superclass (can be null if the class is Object)
     */
    IClassModel getSuperClass();

    /**
     * Returns the List of classes the Model inherits from.
     *
     * @return Intefaces the model inherits.
     */
    Iterable<? extends IClassModel> getInterfaces();

    /**
     * Returns the List of the Model's Has-A relation.
     *
     * @return List of Classes with a Has-A relationship with the Model.
     */
    Iterable<? extends IClassModel> getHasRelation();

    /**
     * Returns the List of the Model's Depends-On Relation.
     *
     * @return List of Classes with a Depends-On relationship with the Model.
     */
    Iterable<? extends IClassModel> getDependsRelation();

    /**
     * Returns the Super Class' name
     *
     * @return Super Class's name.
     */
    String getSuperClassName();

    /**
     * TODO: Fred
     */
    interface IClassType {
        void switchByCase(Switcher switcher);

        interface Switcher {
            void ifAbstract();

            void ifConcrete();

            void ifInterface();

            void ifEnum();
        }
    }

}
