package analyzer.favorComposition;

import analyzer.utility.*;
import config.IConfiguration;
import utility.ClassType;

import java.util.*;

/**
 * Created by lamd on 1/14/2017.
 */
public class FavorCompositionAnalyzer implements IAnalyzer {
    @Override
    public ISystemModel analyze(ISystemModel systemModel, IConfiguration config) {
        FavorCompositionConfiguration favorComConfig = config.createConfiguration(FavorCompositionConfiguration.class);
        Set<? extends IClassModel> classes = systemModel.getClasses();
        Map<ClassPair, List<IRelationInfo>> relations = systemModel.getRelations();

        Set<ClassPair> violators = findViolators(classes);
        classes = updateClasses(violators, favorComConfig, classes);
        relations = updateRelations(violators, favorComConfig, relations);

        return new ProcessedSystemModel(classes, relations);
    }

    private Set<ClassPair> findViolators(Collection<? extends IClassModel> classes) {
        Set<ClassPair> violators = new HashSet<>();
        classes.forEach((clazz) -> {
            if (violateFavorComposition(clazz)) {
                violators.add(new ClassPair(clazz, clazz.getSuperClass()));
            }
        });

        return violators;
    }

    private boolean violateFavorComposition(IClassModel clazz) {
        IClassModel superClass = clazz.getSuperClass();
        return superClass.getType() == ClassType.CONCRETE && !superClass.getName().equals("java.lang.Object");
    }

    private Set<IClassModel> updateClasses(Set<ClassPair> violators, FavorCompositionConfiguration config,
                                           Collection<? extends IClassModel> classes) {
        Set<IClassModel> newClasses = new HashSet<>();
        String nodeStyle = String.format("color=\"%s\"", config.getFavorComColor());
        for (IClassModel clazz : classes) {
            if (violators.contains(new ClassPair(clazz, clazz.getSuperClass()))) {
                newClasses.add(new ClassModelStyleDecorator(clazz, nodeStyle));
            } else {
                newClasses.add(clazz);
            }
        }
        return newClasses;
    }

    private Map<ClassPair, List<IRelationInfo>> updateRelations(Set<ClassPair> violators,
                                                                FavorCompositionConfiguration config, Map<ClassPair, List<IRelationInfo>> relations) {
        Map<ClassPair, List<IRelationInfo>> newRelations = new HashMap<>();
        String format = String.format("color=\"%s\"", config.getFavorComColor());
        relations.forEach((pair, infos) -> {
            List<IRelationInfo> newInfos = new LinkedList<>();
            if (violators.contains(pair)) {
                for (IRelationInfo info : infos) {
                    newInfos.add(new RelationStyleDecorator(info, format));
                }
            } else {
                newInfos = infos;
            }
            newRelations.put(pair, newInfos);
        });
        return newRelations;
    }

}
