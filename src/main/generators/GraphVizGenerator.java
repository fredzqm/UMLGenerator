package generators;

import configs.Configuration;
import analyzers.Job;
import models.*;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * A GraphVizGenerator that outputs DOT files for GraphViz.
 *
 * Created by lamd on 12/7/2016.
 */
public class GraphVizGenerator implements IGenerator {
    private List<GraphVizClass> classes;

    private StringBuilder dotString;

    public GraphVizGenerator() {
        this.classes = new ArrayList<>();
        this.dotString = new StringBuilder();
    }

    public List<GraphVizClass> getClasses() {
        return this.classes;
    }

    public String getDotString() {
        return this.dotString.toString();
    }

    private void parseSystemModel(ISystemModel sm) {
        List<IClassModel> classes = sm.getClasses();
        classes.forEach((model) -> {
            this.classes.add(new GraphVizClass(model));
        });
    }

    private void createDotString() {
        // DOT parent.
        this.dotString.append("digraph GraphVizGeneratedDOT {\n");

        // TODO: This can be configurable.
        // Basic Configurations.
        this.dotString.append("\tnodesep=1.0;\n");
        this.dotString.append("\tnode [shape=record];\n");

        // Basic UML Boxes.
        this.classes.forEach((vizClass) -> {
            this.dotString.append(vizClass.getClassVizDescription());
        });

        // Superclass Relations
        this.dotString.append("\tedge [arrowhead=onormal]\n"); // TODO: Configurable.
        this.classes.forEach((vizClass) -> {
            this.dotString.append("\t");
            this.dotString.append(vizClass.getSuperClassVizDescription());
            this.dotString.append("\n");
        });

        // Inheritance Relations.
        this.dotString.append("\tedge [arrowhead=onormal, style=dashed];\n");
        this.classes.forEach((vizClass) -> {
            this.dotString.append("\t");
            this.dotString.append(vizClass.getInterfaceVizDescription());
            this.dotString.append("\n");
        });

        // Depends Relations.
        this.dotString.append("\tedge [arrowhead=vee];\n");
        this.classes.forEach((vizClass) -> {
            this.dotString.append("\t");
            this.dotString.append(vizClass.getDependsRelationVizDescription());
            this.dotString.append("\n");
        });
    }

    private void writeDotString(String outputDirectory, String outputName) throws IOException {
        File file = new File(outputDirectory);
        file.mkdirs();

        try {
            FileWriter writer = new FileWriter(outputDirectory + "/" + outputName);
            writer.write(this.dotString.toString());
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
            throw new IOException("[ ERROR ]: Unable to create file.");
        }
    }

    @Override
    public void generate(ISystemModel sm, Configuration config, Collection<Job> jobs) { // TODO: figure out what to pull out of the configuration and jobs.
        parseSystemModel(sm);
        createDotString();
        try {
            writeDotString("./output", "animals.dot"); // Configuration should normalize if files do not have extenions.
        } catch (IOException e) {
            e.printStackTrace(); // TODO
        }
    }

    /**
     * An Inner class that represents a single class in the DOT language.
     */
    private class GraphVizClass {
        private String steroType, name;
        private StringBuilder header;
        private StringBuilder fields;
        private StringBuilder methods;
        private StringBuilder classVizDescription;
        private StringBuilder interfaceVizDescription;
        private StringBuilder hasRelationVizDescription;
        private StringBuilder dependsRelationVizDescription;
        private StringBuilder superClassVizDescription;

        private GraphVizClass(IClassModel model) {
            this.name = model.getClassName();
            this.header = new StringBuilder();
            this.fields = new StringBuilder();
            this.methods = new StringBuilder();

            this.interfaceVizDescription = new StringBuilder();
            this.hasRelationVizDescription = new StringBuilder();
            this.dependsRelationVizDescription = new StringBuilder();
            this.classVizDescription = new StringBuilder();
            this.superClassVizDescription = new StringBuilder();

            parseModel(model);
        }

        /**
         * Returns the String of the sterotype.
         * <p>
         * Example: <<Interface>> or <<Abstract>>
         *
         * @return Class' Sterotype.
         */
        public String getSteroType() {
            return this.steroType;
        }

        /**
         * Returns the name of to be displayed on the UML.
         *
         * @return Display name.
         */
        public String getName() {
            return this.name;
        }

        /**
         * Returns String of the header information.
         * It is a composite of the sterotype and name.
         *
         * @return Sterotype and Name.
         */
        public String getHeader() {
            return this.header.toString();
        }

        /**
         * Returns String of the Fields in DOT file format.
         *
         * @return Fields DOT format.
         */
        public String getFields() {
            return this.fields.toString();
        }

        /**
         * Returns String of the methods in DOT file format.
         *
         * @return Methods DOT format.
         */
        public String getMethods() {
            return this.methods.toString();
        }

        /**
         * Returns String of the interfaces in DOT file format.
         *
         * @return Interfaces DOT format.
         */
        public String getInterfaceVizDescription() {
            return this.interfaceVizDescription.toString();
        }

        /**
         * Returns the String of the Has-A relationship of the class in DOT file format.
         *
         * @return Has-A relationship DOT format.
         */
        public String getHasRelationVizDescription() {
            return this.hasRelationVizDescription.toString();
        }

        /**
         * Returns the String of the Depends-On relationship of the class in DOT file format.
         *
         * @return Depends-On relationship DOT format.
         */
        public String getDependsRelationVizDescription() {
            return this.dependsRelationVizDescription.toString();
        }

        /**
         * Returns the String of the SuperClass in DOT file format.
         *
         * @return SuperClass in DOT format.
         */
        public String getSuperClassVizDescription() {
            return this.superClassVizDescription.toString();
        }

        /**
         * Returns the String of the Class (header, fields, methods) in DOT file format.
         *
         * @return Class in DOT format.
         */
        public String getClassVizDescription() {
            return this.classVizDescription.toString();
        }

        private void generateHeader(ClassType type, String className) {
            switch (type) {
                case ABSTRACT:
                    this.steroType = "\\<\\<Abstract\\>\\>\\n";
                    break;
                case INTERFACE:
                    this.steroType = "\\<\\<Interface\\>\\>\\n";
                    break;
                case CONCRETE:
                default:
                    // Use Fall Through to append the class name regardless of Abstrat of Interface typing.
                    this.name = className;
                    this.header.append(this.steroType);
                    this.header.append(this.name);
            }
        }

        private void generateField(IFieldModel field) {
            this.fields.append(field.getModifier().getModifierValue());
            this.fields.append(" ");
            this.fields.append(field.getName());
            this.fields.append(" : ");
            this.fields.append(field.getTypeName());
            this.fields.append("\\l");
        }

        private void generateFields(List<IFieldModel> fields) {
            fields.forEach(this::generateField);
        }

        private void generateMethod(IMethodModel method) {
            // Add the modifier.
            this.methods.append(method.getModifier().getModifierValue());
            this.methods.append(" ");

            // Add the name.
            this.methods.append(method.getName());
            this.methods.append("(");

            // Add the arguments.
            method.getArguments().forEach((type) -> {
                this.methods.append(type.getName());
                this.methods.append(" : ");
                this.methods.append(type.getType());
                this.methods.append(", ");
            });

            // Remove the ", " and end method with parenthesis.
            this.methods.replace(this.methods.length() - 2, this.methods.length(), ")");

            // Add the return type.
            this.methods.append(" : ");
            this.methods.append(method.getReturnType());
            this.methods.append("\\l ");
        }

        private void generateMethods(List<IMethodModel> methods) {
            methods.forEach(this::generateMethod);
        }

        private void generateClassVizDescription() {
            // Set Description block.
            this.classVizDescription.append("\t");
            this.classVizDescription.append(this.name);
            this.classVizDescription.append(" [\n");
//            this.classVizDescription.append("\t\tshape=\"record\",\n"); // TODO: This may change with the configuration

            // Set the header.
            this.classVizDescription.append("\t\tlabel = \"");
            this.classVizDescription.append("{");
            this.classVizDescription.append(this.header);
            this.classVizDescription.append(" | ");

            // Set the fields.
            // Check to avoid double lines if there are no fields.
            if (this.fields.length() != 0) {
                this.classVizDescription.append(this.fields);
                this.classVizDescription.append(" | ");
            }

            // Set the methods.
            this.classVizDescription.append(this.methods);
            this.classVizDescription.append("}\"\n\t];");
        }

        private void setupDependencyVizDescription(StringBuilder visDescription) {
            final String VIZ_ARROW = " -> ";

            visDescription.append(this.name);
            visDescription.append(VIZ_ARROW);
            visDescription.append("{");
        }

        private void generateSuperClassVizDescription(String superClass) {
            setupDependencyVizDescription(this.superClassVizDescription);
            this.superClassVizDescription.append(superClass);
            this.superClassVizDescription.append("};\n");
        }

        private void closeDependencyVizDescription(StringBuilder vizDescription) {
            int length = vizDescription.length();
            vizDescription.replace(length - 2, length, "};\n");
        }

        private void generateInterfaceVizDescription(List<IClassModel> interfaces) {
            setupDependencyVizDescription(this.interfaceVizDescription);
            interfaces.forEach((interfaceModel) -> {
                this.interfaceVizDescription.append(interfaceModel.getClassName());
                this.interfaceVizDescription.append(", ");
            });
            closeDependencyVizDescription(this.interfaceVizDescription);
        }

        private void generateHasRelationVizDescription(List<IClassModel> hasRelation) {
            setupDependencyVizDescription(this.hasRelationVizDescription);
            hasRelation.forEach((has) -> {
                this.hasRelationVizDescription.append(has.getClassName());
                this.hasRelationVizDescription.append(", ");
            });
            closeDependencyVizDescription(this.hasRelationVizDescription);
        }

        private void generateDependsRelationVizDescription(List<IClassModel> dependsRelation) {
            setupDependencyVizDescription(this.dependsRelationVizDescription);
            dependsRelation.forEach((dependency) -> {
                this.dependsRelationVizDescription.append(dependency.getClassName());
                this.dependsRelationVizDescription.append(", ");
            });
            closeDependencyVizDescription(this.dependsRelationVizDescription);
        }

        private void parseModel(IClassModel model) {
            // Get Class information.
            generateHeader(model.getType(), model.getClassName());
            generateFields(model.getFields());
            generateMethods(model.getMethods());

            // Setup the VizDescriptions.
            generateClassVizDescription();
            generateSuperClassVizDescription(model.getSuperClass());
            generateInterfaceVizDescription(model.getInterfaces());
            generateHasRelationVizDescription(model.getHasRelation());
            generateDependsRelationVizDescription(model.getDependsRelation());
        }
    }
}
