package analyzer.utility;

/**
 * An interface for Fields.
 * <p>
 * Created by lamd on 12/9/2016.
 */
public interface IFieldModel extends IClassComponent {

    /**
     * @return the type of this field
     */
    ITypeModel getFieldType();

}
