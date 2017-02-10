package analyzer.utility;

/**
 * An interface for Fields.
 * <p>
 * Created by lamd on 12/9/2016.
 */
public interface IFieldModel extends IClassComponent {
    /**
     * Returns the ITypeModel of the FieldModel.
     *
     * @return the ITypeModel of this field
     */
    ITypeModel getFieldType();

    /**
     * @return the classModel of the fields type, null if this field is an array
     * or primitive type
     */
    IClassModel getClassModel();
}
