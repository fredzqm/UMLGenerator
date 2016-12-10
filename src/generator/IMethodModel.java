package generator;

import java.util.Collection;


/**
 * Created by lamd on 12/7/2016.
 */
public interface IMethodModel {
	IClassModel getParentClass();

	String getName();

	IModifier getModifier();

	Collection<? extends ITypeModel> getArguments();

	ITypeModel getReturnType();

	Collection<? extends IMethodModel> getDependentMethods();

	Collection<? extends IFieldModel> getDependentFields();

	boolean isFinal();
	
}
