package adapter;

import adapter.classParser.ClassParserConfiguration;
import adapter.classParser.IParser;
import analyzer.utility.IClassModel;
import analyzer.utility.IFieldModel;
import analyzer.utility.IMethodModel;
import generator.INode;
import utility.IFilter;
import utility.Modifier;

public class ClassModelNode implements INode {
    private IClassModel classModel;
    private ClassParserConfiguration config;

    public ClassModelNode(IClassModel classModel, ClassParserConfiguration config) {
        this.classModel = classModel;
        this.config = config;
    }

    public String getLabel() {
        IFilter<Modifier> modifierFilter = config.getModifierFilters();
        IParser<IClassModel> header = config.getHeaderParser();
        IParser<IFieldModel> fieldParser = config.getFieldParser();
        IParser<IMethodModel> methodParser = config.getMethodParser();

        StringBuilder sb = new StringBuilder();
        // Set the header.

        sb.append(header.parse(classModel, config));
        // Filter the fields
        Iterable<? extends IFieldModel> fields = classModel.getFields();
        IFilter<IFieldModel> fieldFilters = (f) -> modifierFilter.filter(f.getModifier());
        fields = fieldFilters.filter(fields);
        // Render the fields
        if (fields.iterator().hasNext()) {
            sb.append(String.format(" | %s", fieldParser.parse(fields, config)));
        }

        // Filter the methods
        Iterable<? extends IMethodModel> methods = classModel.getMethods();
        IFilter<IMethodModel> methodFilters = (m) -> modifierFilter.filter(m.getModifier());
        methods = methodFilters.filter(methods);
        // Render the methods
        if (methods.iterator().hasNext()) {
            sb.append(String.format(" | %s", methodParser.parse(methods, config)));
        }

        // Generate the full string with the label text generated above.
        return sb.toString();
    }

    @Override
    public String getName() {
        return classModel.getName();
    }

    @Override
    public String getNodeStyle() {
        return classModel.getNodeStyle();
    }

}
