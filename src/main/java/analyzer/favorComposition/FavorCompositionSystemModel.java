package analyzer.favorComposition;

import analyzer.utility.ClassPair;
import analyzer.utility.IClassModel;
import analyzer.utility.IRelationInfo;
import analyzer.utility.ISystemModel;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * Created by lamd on 1/15/2017.
 */
public class FavorCompositionSystemModel implements ISystemModel {
    private ISystemModel systemModel;
    private Collection<? extends IClassModel> classes;

    FavorCompositionSystemModel(ISystemModel systemModel, Collection<? extends IClassModel> classes) {
        this.systemModel = systemModel;
        this.classes = classes;
    }

    @Override
    public Collection<? extends IClassModel> getClasses() {
        return this.classes;
    }

    @Override
    public Map<ClassPair, List<IRelationInfo>> getRelations() {
        return this.systemModel.getRelations();
    }
}
