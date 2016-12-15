package generator;

/**
 * A GraphVizDepenedency Formatter.
 * <p>
 * It provides common setup methods for Dependancy base relations.
 * <p>
 * Created by lamd on 12/14/2016.
 */
public class GraphvizDependencyFormatter {
    public static void setupDependencyVizDescription(StringBuilder visDescription, String name) {
        final String VIZ_ARROW = " -> ";

        visDescription.append("\"").append(name).append("\"");
        visDescription.append(VIZ_ARROW);
        visDescription.append("{");
    }

    public static void closeDependencyVizDescription(StringBuilder vizDescription, int lengthBefore) {
        int length = vizDescription.length();

        // Ensure that it has changed.
        if (lengthBefore == length) {
            vizDescription.append("}");
        } else {
            vizDescription.replace(length - 2, length, "};\n");
        }
    }
}
