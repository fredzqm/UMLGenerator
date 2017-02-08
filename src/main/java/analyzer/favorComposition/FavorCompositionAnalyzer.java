package analyzer.favorComposition;

import analyzer.relationParser.RelationExtendsClass;
import analyzer.utility.IAnalyzer;
import analyzer.utility.IClassModel;
import analyzer.utility.ISystemModel;
import config.IConfiguration;
import utility.ClassType;

/**
 * Favor Composition Pattern Analyzer. It will flag classes that breaks the principle Favor Composition over Inheritance.
 * <p>
 * Created by lamd on 1/14/2017.
 */
public class FavorCompositionAnalyzer implements IAnalyzer {
    @Override
    public void analyze(ISystemModel systemModel, IConfiguration iConfig) {
        FavorCompositionConfiguration config = iConfig.createConfiguration(FavorCompositionConfiguration.class);

        systemModel.getClasses().stream()
                .filter(this::violateFavorComposition)
                .forEach((clazz) -> {
                    systemModel.addClassModelStyle(clazz, "color", config.getFavorComColor());
                    systemModel.addStyleToRelation(clazz, clazz.getSuperClass(), RelationExtendsClass.REL_KEY, "color", config.getFavorComColor());
                });
    }

    private boolean violateFavorComposition(IClassModel clazz) {
        IClassModel superClass = clazz.getSuperClass();
        return superClass.getType() == ClassType.CONCRETE && !superClass.getName().equals("java.lang.Object");
    }

}
