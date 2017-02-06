package analyzer.decorator;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import analyzer.utility.IAnalyzer;
import analyzer.utility.IClassModel;
import analyzer.utility.IFieldModel;
import analyzer.utility.ISystemModel;
import config.IConfiguration;

public abstract class PatternAnalyzer implements IAnalyzer {

    @Override
    public void analyze(ISystemModel systemModel, IConfiguration config) {
        
        initialize(systemModel, config);
        
        Set<? extends IClassModel> classlist = systemModel.getClasses();
        for (IClassModel clazz : classlist) {
            List<IClassModel> composes = getComposeClasses(clazz, classlist);
            List<IClassModel> superClasses = getSuperClasses(clazz, classlist);
            for (IClassModel comp : composes) {
                for (IClassModel sup : superClasses) {
                    acceptPossiblePattern(systemModel, clazz, comp, sup);
                }
            }
        }
    }

    public abstract void initialize(ISystemModel systemModel, IConfiguration config);

    /**
     * process possible pattern pairs
     *
     * @param clazz
     * @param composedClass
     * @param superClass
     */
    public abstract void acceptPossiblePattern(ISystemModel systemModel, IClassModel clazz, IClassModel composedClass,
            IClassModel superClass);

    private List<IClassModel> getComposeClasses(IClassModel clazz, Set<? extends IClassModel> classls) {
        List<IClassModel> composes = new ArrayList<>();
        for (IFieldModel f : clazz.getFields()) {
            IClassModel classModel = f.getFieldType().getClassModel();
            if (classModel != null && classls.contains(classModel)) {
                composes.add(classModel);
            }
        }
        return composes;
    }

    private List<IClassModel> getSuperClasses(IClassModel clazz, Set<? extends IClassModel> classls) {
        List<IClassModel> ls = new ArrayList<>();
        addSuperClasses(ls, clazz, classls);
        return ls;
    }

    private void addSuperClasses(List<IClassModel> ls, IClassModel clazz, Set<? extends IClassModel> classls) {
        if (clazz == null || !classls.contains(clazz))
            return;
        ls.add(clazz);
        addSuperClasses(ls, clazz.getSuperClass(), classls);
        for (IClassModel interf : clazz.getInterfaces()) {
            addSuperClasses(ls, interf, classls);
        }
    }

}
