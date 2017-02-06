package analyzer.favorComposition;

import analyzer.utility.IAnalyzer;
import analyzer.utility.IClassModel;
import analyzer.utility.ISystemModel;
import config.IConfiguration;
import utility.ClassType;

/**
 * Created by lamd on 1/14/2017.
 */
public class FavorCompositionAnalyzer implements IAnalyzer {
    @Override
    public void analyze(ISystemModel systemModel, IConfiguration iConfig) {
        FavorCompositionConfiguration config = iConfig.createConfiguration(FavorCompositionConfiguration.class);

        for (IClassModel clazz : systemModel.getClasses()) {
            if (violateFavorComposition(clazz)) {
                systemModel.addClassModelStyle(clazz, "color", config.getFavorComColor());
                systemModel.addStyleToRelation(clazz, clazz.getSuperClass(), "extend", "color",
                        config.getFavorComColor());

            }
        }
    }

    private boolean violateFavorComposition(IClassModel clazz) {
        IClassModel superClass = clazz.getSuperClass();
        return superClass.getType() == ClassType.CONCRETE && !superClass.getName().equals("java.lang.Object");
    }

}
