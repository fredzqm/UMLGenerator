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

    @Override
    public ISystemModel analyze(ISystemModel systemModel, IConfiguration config) {
        Set<? extends IClassModel> classes = systemModel.getClasses();
        Map<ClassPair, List<IRelationInfo>> relations = systemModel.getRelations();

        setUp(classes, relations, config);

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
     * set up the classes information
     * 
     * @param classes
     * @param relations
     * @param config
     */
    public abstract void setUp(Set<? extends IClassModel> classes, Map<ClassPair, List<IRelationInfo>> relations,
            IConfiguration config);

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
