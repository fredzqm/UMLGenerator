package analyzer.favorComposition;

import analyzer.utility.IClassModel;
import analyzer.utility.ISystemModel;
import analyzer.utility.ISystemModelFilter;
import utility.ClassType;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by lamd on 1/15/2017.
 */
public class FavorCompositionSystemModel extends ISystemModelFilter {
    private FavorCompositionConfiguration config;

    FavorCompositionSystemModel(ISystemModel systemModel, FavorCompositionConfiguration favorComConfig) {
        super(systemModel);
        this.config = favorComConfig;
    }

    @Override
    public Collection<? extends IClassModel> getClasses() {
        Collection<IClassModel> analyzedClass = new ArrayList<>();
        super.getClasses().forEach((c) -> {
            if (violateFavorComposition(c)) {
                analyzedClass.add(new FavorCompositionClassModel(c, config));
            } else {
                analyzedClass.add(c);
            }
        });
        return analyzedClass;
    }

    private boolean violateFavorComposition(IClassModel clazz) {
        IClassModel superClass = clazz.getSuperClass();
        return superClass.getType() == ClassType.CONCRETE && !superClass.getName().equals("java.lang.Object");
    }

}
