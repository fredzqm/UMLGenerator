package generator;

/**
 * A GraphVizDepenedency Formatter.
 * <p>
 * It provides common setup methods for Dependency base relations.
 * <p>
 * Created by lamd on 12/14/2016.
 */
public class GraphVizDependencyFormatter {
    static void setupDependencyVizDescription(StringBuilder visDescription, String name) {
        final String VIZ_ARROW = " -> ";

        visDescription.append(String.format("\t\"%s\"%s{", name, VIZ_ARROW));
    }

    static void closeDependencyVizDescription(StringBuilder vizDescription, int lengthBefore) {
        int length = vizDescription.length();

        // Ensure that it has changed.
        if (lengthBefore == length) {
            vizDescription.append("};\n");
        } else {
            vizDescription.replace(length - 1, length, "};\n");
        }
    }
}
