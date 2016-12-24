package analyzer;

import generator.ISystemModel;
import generator.classParser.IClassModel;
import generator.relParser.IRelation;
import generator.relParser.Relation;
import generator.relParser.RelationDecBidir;
import generator.relParser.RelationHasA;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AnalyzedSystemModel implements ISystemModel {
    private ISystemModel systemModel;

    AnalyzedSystemModel(ISystemModel sm) {
        this.systemModel = sm;
    }

    @Override
    public Iterable<? extends IClassModel> getClasses() {
        return systemModel.getClasses();
    }

    @Override
    public Iterable<IRelation> getRelations() {
        List<IRelation> relations = new ArrayList<>();
        systemModel.getRelations().forEach(relations::add);

        Collections.sort(relations);
        mergeBijectiveRelations(relations);

        return relations;
    }

    private void mergeBijectiveRelations(List<IRelation> relations) {
    	IRelation current;
    	IRelation next;
        for (int i = 0; i < relations.size() - 1; i++) {
            current = relations.get(i);
            next = relations.get(i + 1);

            // Ensure that current is of the same class before attempting to remove relations.
            if (current.getClass().equals(next.getClass()) && current.getFrom().equals(next.getTo())) {
                relations.remove(next);
                relations.set(i, new RelationDecBidir(current));

                // Save cardinality information inside current.
//                if (current instanceof RelationHasA) {
//                    RelationHasA currentHas = (RelationHasA) current;
//                    RelationHasA nextHas = (RelationHasA) next;
//                    current.setCardinalityTo(currentHas.getCount());
//                    current.setCardinalityFrom(nextHas.getCount());
//                }
            }
        }
    }
}
