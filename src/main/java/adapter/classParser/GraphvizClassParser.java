package adapter.classParser;

import analyzer.utility.IClassModel;
import analyzer.utility.IFieldModel;
import analyzer.utility.IMethodModel;
import analyzer.utility.ISystemModel;
import utility.IFilter;
import utility.Modifier;

public class GraphvizClassParser implements IParser<IClassModel> {

    @Override
    public String parse(IClassModel classModel,ISystemModel systemModel, ClassParserConfiguration config) {
        IFilter<Modifier> modifierFilter = config.getModifierFilters();
        IParser<IFieldModel> fieldParser = config.getFieldParser();
        IParser<IClassModel> headerParser = config.getHeaderParser();
        IParser<IMethodModel> methodParser = config.getMethodParser();

        StringBuilder sb = new StringBuilder();
        
        sb.append(headerParser.parse(classModel, systemModel, config));
        // Filter the fields
        Iterable<? extends IFieldModel> fields = classModel.getFields();
        IFilter<IFieldModel> fieldFilters = (f) -> modifierFilter.filter(f.getModifier());
        fields = fieldFilters.filter(fields);
        // Render the fields
        if (fields.iterator().hasNext()) {
            sb.append(String.format(" | %s", fieldParser.parse(fields, systemModel, config)));
        }

        // Filter the methods
        Iterable<? extends IMethodModel> methods = classModel.getMethods();
        IFilter<IMethodModel> methodFilters = (m) -> modifierFilter.filter(m.getModifier());
        methods = methodFilters.filter(methods);
        // Render the methods
        if (methods.iterator().hasNext()) {
            sb.append(String.format(" | %s", methodParser.parse(methods,systemModel, config)));
        }

        // Generate the full string with the label text generated above.
        return sb.toString();
    }

}
