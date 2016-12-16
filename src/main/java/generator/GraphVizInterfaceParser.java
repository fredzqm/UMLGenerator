package generator;

/**
 * A GraphVizParser for the model's interface.
 * <p>
 * Created by lamd on 12/14/2016.
 */
public class GraphVizInterfaceParser implements IParser<IClassModel> {

    @Override
    public String parse(IClassModel thisClass) {
        Iterable<? extends IClassModel> otherClassList = thisClass.getInterfaces();

        StringBuilder sb = new StringBuilder();
        GraphVizDependencyFormatter.setupDependencyVizDescription(sb, thisClass.getName());
        int interfaceLengthBefore = sb.length();

        otherClassList.forEach((interfaceModel) -> {
            sb.append(String.format("\"%s\" ", interfaceModel.getName()));
        });

        // If it is empty close the braces without replacing characters.
        GraphVizDependencyFormatter.closeDependencyVizDescription(sb, interfaceLengthBefore);

        return sb.toString();
    }

}