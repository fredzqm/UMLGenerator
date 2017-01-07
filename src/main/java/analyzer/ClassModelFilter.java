package analyzer;

import java.util.List;
import java.util.Map;

import utility.Modifier;

public class ClassModelFilter implements IClassModel {
	private IClassModel classModel;

	public ClassModelFilter(IClassModel classModel) {
		this.classModel = classModel;
	}

	protected IClassModel getClassModel() {
		return classModel;
	}

	@Override
	public String getName() {
		return classModel.getName();
	}

	@Override
	public String getLabel() {
		return classModel.getLabel();
	}
	
	@Override
	public Iterable<? extends IFieldModel> getFields() {
		return classModel.getFields();
	}

	@Override
	public Iterable<? extends IMethodModel> getMethods() {
		return classModel.getMethods();
	}

	@Override
	public Modifier getModifier() {
		return classModel.getModifier();
	}

	@Override
	public List<String> getStereoTypes() {
		return classModel.getStereoTypes();
	}

	@Override
	public IClassModel getSuperClass() {
		return classModel.getSuperClass();
	}

	@Override
	public Iterable<? extends IClassModel> getInterfaces() {
		return classModel.getInterfaces();
	}

	@Override
	public Map<? extends IClassModel, Integer> getHasRelation() {
		return classModel.getHasRelation();
	}

	@Override
	public Iterable<? extends IClassModel> getDependsRelation() {
		return classModel.getDependsRelation();
	}


}
