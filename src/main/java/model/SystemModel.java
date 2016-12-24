package model;

import generator.ISystemModel;
import generator.relParser.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * This class representing the entire model of a java program
 */
public class SystemModel implements ISystemModel {
    private Collection<ClassModel> classList;

    private SystemModel(Collection<ClassModel> importantList) {
        this.classList = importantList;
    }

    /**
     * constructs an instance of SystemModel given the configuration
     *
     * @param config
     * @return
     */
    public static SystemModel getInstance(IModelConfiguration config) {
        Iterable<String> importClassesList = config.getClasses();
        if (importClassesList == null)
            throw new RuntimeException("important classes list cannot be null!");

        int recursiveFlag;
        if (config.isRecursive()) {
            recursiveFlag = ASMParser.RECURSE_INTERFACE | ASMParser.RECURSE_SUPERCLASS | ASMParser.RECURSE_HAS_A;
        } else {
            recursiveFlag = 0;
        }

        Collection<ClassModel> ls = ASMParser.getClasses(importClassesList, recursiveFlag);
        return new SystemModel(ls);
    }

    @Override
    public Iterable<ClassModel> getClasses() {
        return classList;
    }

    @Override
    public Iterable<IRelation> getRelations() {
        List<IRelation> ls = new ArrayList<>();
        for (ClassModel classModel : classList) {
            String className = classModel.getName();
            
            // add related super class relationship
            ClassModel superClass = classModel.getSuperClass();
            if (superClass != null)
                if (classList.contains(superClass))
                    ls.add(new RelationExtendsClass(className, superClass.getName()));

            // add related interface relationship
            Iterable<ClassModel> interfaces = classModel.getInterfaces();
            for (ClassModel x : interfaces)
                if (classList.contains(x))
                    ls.add(new RelationImplement(className, x.getName()));

            // add related has-a relationship
            Map<ClassModel, Integer> has_a = classModel.getHasRelation();
            for (ClassModel x : has_a.keySet())
                if (classList.contains(x))
                    ls.add(new RelationHasA(className, x.getName(), has_a.get(x)));

            // add related depends on relationship
            Iterable<ClassModel> depends_on = classModel.getDependsRelation();
            for (ClassModel x : depends_on)
                if (classList.contains(x))
                    ls.add(new RelationDependsOn(className, x.getName()));
        }
        return ls;
    }

}
