package analyzer;

import generator.ISystemModel;
import generator.classParser.IClassModel;
import generator.relParser.Relation;
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
    public Iterable<Relation> getRelations() {
        List<Relation> relations = new ArrayList<>();
        systemModel.getRelations().forEach(relations::add);

        Collections.sort(relations);
        mergeBijectiveRelations(relations);

        return relations;
    }

    private void mergeBijectiveRelations(List<Relation> relations) {
        Relation current;
        Relation next;
        for (int i = 0; i < relations.size() - 1; i++) {
            current = relations.get(i);
            next = relations.get(i + 1);

            // Ensure that current is of the same class before attempting to remove relations.
            if (current.getClass().equals(next.getClass()) && current.getFrom().equals(next.getTo())) {
                relations.remove(next);
                current.setBijective(true);

                // Save cardinality information inside current.
                if (current instanceof RelationHasA) {
                    RelationHasA currentHas = (RelationHasA) current;
                    RelationHasA nextHas = (RelationHasA) next;
                    current.setCardinalityTo(currentHas.getCount());
                    current.setCardinalityFrom(nextHas.getCount());
                }
            }
        }
    }
}
