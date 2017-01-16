package analyzer.favorComposition;

import analyzer.utility.IAnalyzer;
import analyzer.utility.IAnalyzerConfiguration;
import analyzer.utility.IClassModel;
import analyzer.utility.ISystemModel;
import utility.ClassType;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by lamd on 1/14/2017.
 */
public class FavorCompositionAnalyzer implements IAnalyzer {
    @Override
    public ISystemModel analyze(ISystemModel systemModel, IAnalyzerConfiguration config) {
        Collection<? extends IClassModel> classes = systemModel.getClasses();
        Collection<IClassModel> analyzedClass = new ArrayList<>();

        classes.forEach((c) -> {
            IClassModel superClass = c.getSuperClass();
            if (!superClass.getName().equals("java.lang.Object") && superClass.getType() == ClassType.CONCRETE) {
                analyzedClass.add(new FavorCompositionClassModel(c));
            } else {
                analyzedClass.add(c);
            }
        });

        return new FavorCompositionSystemModel(systemModel, analyzedClass);
    }
}
