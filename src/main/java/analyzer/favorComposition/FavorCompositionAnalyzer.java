package analyzer.favorComposition;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import analyzer.utility.ClassPair;
import analyzer.utility.EdgeStyleRelationDecorator;
import analyzer.utility.IAnalyzer;
import analyzer.utility.IClassModel;
import analyzer.utility.IRelationInfo;
import analyzer.utility.ISystemModel;
import analyzer.utility.NodeStyleClassModelDecorator;
import analyzer.utility.ProcessedSystemModel;
import config.IConfiguration;
import utility.ClassType;

/**
 * Created by lamd on 1/14/2017.
 */
public class FavorCompositionAnalyzer implements IAnalyzer {
    @Override
    public ISystemModel analyze(ISystemModel systemModel, IConfiguration config) {
        FavorCompositionConfiguration favorComConfig = config.createConfiguration(FavorCompositionConfiguration.class);
        Collection<? extends IClassModel> classes = systemModel.getClasses();
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

    private Collection<IClassModel> updateClasses(Set<ClassPair> violators, FavorCompositionConfiguration config,
            Collection<? extends IClassModel> classes) {
        Collection<IClassModel> newClasses = new ArrayList<>();
        String nodeStyle = String.format("color=\"%s\"", config.getFavorComColor());
        for (IClassModel clazz : classes) {
            if (violators.contains(new ClassPair(clazz, clazz.getSuperClass()))) {
                newClasses.add(new NodeStyleClassModelDecorator(clazz, nodeStyle));
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
                    newInfos.add(new EdgeStyleRelationDecorator(info, format));
                }
            } else {
                newInfos = infos;
            }
            newRelations.put(pair, newInfos);
        });
        return newRelations;
    }

}
