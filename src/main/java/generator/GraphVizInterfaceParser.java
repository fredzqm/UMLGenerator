package generator;

/**
 * A GraphVizParser for the model's interface.
 *
 * Created by lamd on 12/14/2016.
 */
public class GraphVizInterfaceParser implements IRelationParser {

	@Override
	public String parse(IClassModel thisClass, IClassModel otherClass) {
		StringBuilder sb = new StringBuilder();
		sb.append("\"").append(otherClass.getName()).append("\"");
		sb.append(", ");
		return sb.toString();
	}

	@Override
	public String parse(IClassModel thisClass, Iterable<? extends IClassModel> otherClassLs) {
		StringBuilder sb = new StringBuilder();
		GraphVizDependencyFormatter.setupDependencyVizDescription(sb, thisClass.getName());
		int interfaceLengthBefore = sb.length();

		otherClassLs.forEach((interfaceModel) -> {
			sb.append(parse(thisClass, interfaceModel));
		});

		// If it is empty close the braces without replacing characters.
		GraphVizDependencyFormatter.closeDependencyVizDescription(sb, interfaceLengthBefore);
		return sb.toString();
	}

}
