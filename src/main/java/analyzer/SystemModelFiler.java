package analyzer;

import java.util.Collection;

import analyzerRelationParser.Relation;

public class SystemModelFiler implements IASystemModel {
	private IASystemModel systemModel;

	public SystemModelFiler(IASystemModel systemModel) {
		this.systemModel = systemModel;
	}

	protected IASystemModel getSystemModel() {
		return systemModel;
	}

	@Override
	public Collection<? extends IClassModel> getClasses() {
		return systemModel.getClasses();
	}

	@Override
	public Iterable<Relation> getRelations() {
		return systemModel.getRelations();
	}

}
