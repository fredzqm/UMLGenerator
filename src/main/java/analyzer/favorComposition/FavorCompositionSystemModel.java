package analyzer.favorComposition;

import analyzer.utility.*;
import utility.ClassType;

import java.util.*;

/**
 * Created by lamd on 1/15/2017.
 */
public class FavorCompositionSystemModel extends ISystemModelFilter {
    private FavorCompositionConfiguration config;
    private Collection<IClassModel> classes;
    private Map<ClassPair, List<IRelationInfo>> relations;

    FavorCompositionSystemModel(ISystemModel systemModel, FavorCompositionConfiguration favorComConfig) {
        super(systemModel);
        this.config = favorComConfig;
        this.classes = new ArrayList<>();
        this.relations = new HashMap<>();
        processModel();
    }

    private void processModel() {
        Set<ClassPair>  violators = findViolators();
        updateClasses(violators);
        updateRelations(violators);
    }

    private Set<ClassPair> findViolators() {
        Set<ClassPair> violators = new HashSet<>();
        super.getClasses().forEach((clazz) -> {
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

    private void updateClasses(Set<ClassPair> violators) {
        super.getClasses().forEach((clazz) -> {
            if (violators.contains(new ClassPair(clazz, clazz.getSuperClass()))) {
                this.classes.add(new FavorCompositionClassModel(clazz, this.config));
            } else {
                this.classes.add(clazz);
            }
        });
    }

    private void updateRelations(Set<ClassPair> violators) {
        super.getRelations().forEach((pair, infos) -> {
            List<IRelationInfo> newInfos = new LinkedList<>();
            if (violators.contains(pair)) {
                for (IRelationInfo info : infos) {
                    newInfos.add(new FavorCompositionRelation(info, this.config));
                }
            }
            this.relations.put(pair, newInfos);
        });
    }

    @Override
    public Collection<? extends IClassModel> getClasses() {
        return this.classes;
    }

    @Override
    public Map<ClassPair, List<IRelationInfo>> getRelations() {
        return this.relations;
    }
}
