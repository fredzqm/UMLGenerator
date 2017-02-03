package analyzer.decorator;

import analyzer.utility.*;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * Created by lamd on 2/2/2017.
 */
public class AdapterDecoratorSystemModel extends ISystemModelFilter {
    /**
     * Construct a ISystemModel Filter.
     *
     * @param systemModel
     */
    public AdapterDecoratorSystemModel(ISystemModel systemModel) {
        super(systemModel);
    }

    @Override
    public Collection<? extends IClassModel> getClasses() {
        super.
        return null;
    }

    @Override
    public Map<ClassPair, List<IRelationInfo>> getRelations() {
        return null;
    }
}
