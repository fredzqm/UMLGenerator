package analyzer.pattern;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import analyzer.utility.ClassPair;
import analyzer.utility.IAnalyzer;
import analyzer.utility.IClassModel;
import analyzer.utility.IFieldModel;
import analyzer.utility.IRelationInfo;
import analyzer.utility.ISystemModel;
import config.IConfiguration;

/**
 * 
 * @author zhang
 *
 */
public abstract class PatternAnalyzer implements IAnalyzer {
    protected Set<? extends IClassModel> classes;
    protected Map<ClassPair, List<IRelationInfo>> relations;

    @Override
    public ISystemModel analyze(ISystemModel systemModel, IConfiguration config) {
        classes = systemModel.getClasses();
        relations = systemModel.getRelations();

        for (IClassModel clazz : classes) {
            List<IClassModel> composes = getComposeClasses(clazz, classes);
            List<IClassModel> superClasses = getSuperClasses(clazz, classes);
            for (IClassModel comp : composes) {
                for (IClassModel sup : superClasses) {
                    acceptPossiblePattern(clazz, comp, sup);
                }
            }
        }
        
        return getProcessedSystemModel();
    }


    /**
     * process possible pattern pairs
     * 
     * @param clazz
     * @param comp
     * @param sup
     * @return
     */
    public abstract boolean acceptPossiblePattern(IClassModel clazz, IClassModel comp, IClassModel sup);

    /**
     * based on the classes inspect generate the styled systemModel
     * 
     * @return
     */
    public abstract ISystemModel getProcessedSystemModel();

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
