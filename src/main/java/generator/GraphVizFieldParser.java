package generator;

import java.util.Collection;

/**
 * A GraphVizParser for the Field class.
 *
 * Created by lamd on 12/14/2016.
 */
public class GraphVizFieldParser implements IGraphVizParser {
    private StringBuilder classFields;

    public GraphVizFieldParser(Iterable<? extends IFieldModel> fields, Collection<IModifier> filters) {
        this.classFields = new StringBuilder();
        generateFields(fields, filters);
    }

    @Override
    public String getOutput() {
        return this.classFields.toString();
    }

    private void generateFields(Iterable<? extends IFieldModel> fields, Collection<IModifier> filters) {
        fields.forEach((field) -> {
            if (!filters.contains(field.getModifier())) {
                this.classFields.append(field.getModifier().getModifierSymbol());
                this.classFields.append(" ");
                this.classFields.append(field.getName());
                this.classFields.append(" : ");
                this.classFields.append(field.getType().getName());
                this.classFields.append("\\l");
            }
        });
    }
}
