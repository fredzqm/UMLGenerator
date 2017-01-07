package analyzer;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * A filter for ISystemModel
 * 
 * @author zhang
 *
 */
public class ISystemModelFilter implements ISystemModel {
	private ISystemModel systemModel;

	public ISystemModelFilter(ISystemModel systemModel) {
		this.systemModel = systemModel;
	}

	protected ISystemModel getSystemModel() {
		return systemModel;
	}

	@Override
	public Collection<? extends IClassModel> getClasses() {
		return systemModel.getClasses();
	}

	@Override
	public Map<ClassPair, List<IRelationInfo>> getRelations() {
		return systemModel.getRelations();
	}

}
