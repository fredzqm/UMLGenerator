package generator;

/**
 * Created by lamd on 12/9/2016.
 */
public interface IFieldModel {
    IModifier getModifier();

    String getName();

    ITypeModel getType();
}
