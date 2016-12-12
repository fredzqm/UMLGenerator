package generator;

import analyzer.Job;
import com.sun.corba.se.impl.orbutil.graph.Graph;
import configs.Configuration;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * A GraphVizGenerator that outputs DOT files for GraphViz.
 * <p>
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
        Iterable<? extends IClassModel> classes = sm.getClasses();
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
            this.dotString.append("\n");
        });

        // Superclass Relations
        this.dotString.append("\tedge [arrowhead=onormal]\n"); // TODO:
        // Configurable.
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

        // Has-A Relations.
        this.dotString.append("\tedge [arrowhead=vee];\n");
        this.classes.forEach((vizClass) -> {
            this.dotString.append("\t");
            this.dotString.append(vizClass.getHasRelationVizDescription());
            this.dotString.append("\n");
        });

        // Depend-On Relations.
        this.dotString.append("\tedge [arrowhead=vee style=dashed];\n");
        this.classes.forEach((vizClass) -> {
            this.dotString.append("\t");
            this.dotString.append(vizClass.getDependsRelationVizDescription());
            this.dotString.append("\n");
        });

        this.dotString.append("}");
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
    // TODO: figure out what to pull out of the configuration and jobs.
    public void generate(ISystemModel sm, Configuration config, Collection<Job> jobs) {
        parseSystemModel(sm);
        createDotString();
        try {
            // Configuration should be normalize if files they do not have extenions.
            // TODO: make this rely on the config.
            writeDotString("./output", "animals.dot");
        } catch (IOException e) {
            e.printStackTrace();
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
            this.name = model.getName();
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
         * Returns String of the header information. It is a composite of the
         * sterotype and name.
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
        String getInterfaceVizDescription() {
            return this.interfaceVizDescription.toString();
        }

        /**
         * Returns the String of the Has-A relationship of the class in DOT file
         * format.
         *
         * @return Has-A relationship DOT format.
         */
        String getHasRelationVizDescription() {
            return this.hasRelationVizDescription.toString();
        }

        /**
         * Returns the String of the Depends-On relationship of the class in DOT
         * file format.
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
        String getSuperClassVizDescription() {
            return this.superClassVizDescription.toString();
        }

        /**
         * Returns the String of the Class (header, fields, methods) in DOT file
         * format.
         *
         * @return Class in DOT format.
         */
        String getClassVizDescription() {
            return this.classVizDescription.toString();
        }

        private void generateHeader(IClassModel.IClassType type, String className) {
            type.switchByCase(new IClassModel.IClassType.Switcher() {
                @Override
                public void ifInterface() {
                    GraphVizClass.this.steroType = "\\<\\<Interface\\>\\>\\n";
                    GraphVizClass.this.name = className;
                    GraphVizClass.this.header.append(GraphVizClass.this.steroType);
                    GraphVizClass.this.header.append(GraphVizClass.this.name);
                }

                @Override
                public void ifConcrete() {
                    GraphVizClass.this.name = className;
                    GraphVizClass.this.steroType = null;
                    GraphVizClass.this.header.append(GraphVizClass.this.name);
                }

                @Override
                public void ifAbstract() {
                    GraphVizClass.this.steroType = "\\<\\<Abstract\\>\\>\\n";
                    GraphVizClass.this.name = className;
                    GraphVizClass.this.header.append(steroType);
                    GraphVizClass.this.header.append(GraphVizClass.this.name);
                }

                @Override
                public void ifEnum() {
                    GraphVizClass.this.steroType = "\\<\\<Enumeration\\>\\>\n";
                    GraphVizClass.this.name = className;
                    GraphVizClass.this.header.append(GraphVizClass.this.steroType);
                    GraphVizClass.this.header.append(GraphVizClass.this.name);
                }
            });
        }

        private void generateField(IFieldModel field) {
            this.fields.append(field.getModifier().getModifierSymbol());
            this.fields.append(" ");
            this.fields.append(field.getName());
            this.fields.append(" : ");
            this.fields.append(field.getType().getName());
            this.fields.append("\\l");
        }

        private void generateFields(Iterable<? extends IFieldModel> iterable) {
            iterable.forEach(this::generateField);
        }

        private void generateMethod(IMethodModel method) {
            // Add the modifier.
            this.methods.append(method.getModifier().getModifierSymbol());
            this.methods.append(" ");

            // Add the name.
            this.methods.append(method.getName());
            this.methods.append("(");

            // Add the arguments.
            int methodLengthBefore = this.methods.length();
            method.getArguments().forEach((type) -> {
                this.methods.append(type.getName());
                // Java does not keep track of variable names.
//                this.methods.append(" : ");
//                this.methods.append(type.getName());
                this.methods.append(", ");
            });

            // Remove the ", " and end method with parenthesis.
            if (methodLengthBefore != this.methods.length()) {
                this.methods.replace(this.methods.length() - 2, this.methods.length(), ")");
            } else {
                this.methods.append(")");
            }

            // Add the return type.
            this.methods.append(" : ");
            this.methods.append(method.getReturnType().getName());
            this.methods.append("\\l ");
        }

        private void generateMethods(Iterable<? extends IMethodModel> iterable) {
            iterable.forEach(this::generateMethod);
        }

        private void generateClassVizDescription() {
            // Set Description block.
            this.classVizDescription.append("\t");
            this.classVizDescription.append("\"" + this.name + "\"");
            this.classVizDescription.append(" [\n");
            // TODO: This may change with the configuration

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

            visDescription.append("\"" + this.name + "\"");
            visDescription.append(VIZ_ARROW);
            visDescription.append("{");
        }

        private void generateSuperClassVizDescription(String superClass) {
            setupDependencyVizDescription(this.superClassVizDescription);

            if (superClass != null) {
                this.superClassVizDescription.append("\"" + superClass + "\"");
            }
            this.superClassVizDescription.append("};\n");
        }

        private void closeDependencyVizDescription(StringBuilder vizDescription, int lengthBefore) {
            int length = vizDescription.length();

            // Ensure that it has changed.
            if (lengthBefore == length) {
                vizDescription.append("}");
            } else {
                vizDescription.replace(length - 2, length, "};\n");
            }
        }

        private void generateInterfaceVizDescription(Iterable<? extends IClassModel> iterable) {
            setupDependencyVizDescription(this.interfaceVizDescription);
            int interfaceLengthBefore = this.interfaceVizDescription.length();

            iterable.forEach((interfaceModel) -> {
                this.interfaceVizDescription.append("\"" + interfaceModel.getName() + "\"");
                this.interfaceVizDescription.append(", ");
            });

            // If it is empty close the braces without replacing characters.
            closeDependencyVizDescription(this.interfaceVizDescription, interfaceLengthBefore);
        }

        private void generateHasRelationVizDescription(Iterable<? extends IClassModel> iterable) {
            setupDependencyVizDescription(this.hasRelationVizDescription);
            int hasALengthBefore = this.hasRelationVizDescription.length();

            iterable.forEach((has) -> {
                this.hasRelationVizDescription.append("\"" + has.getName() + "\"");
                this.hasRelationVizDescription.append(", ");
            });

            closeDependencyVizDescription(this.hasRelationVizDescription, hasALengthBefore);
        }

        private void generateDependsRelationVizDescription(Iterable<? extends IClassModel> iterable) {
            setupDependencyVizDescription(this.dependsRelationVizDescription);
            int dependencyLengthBefore = this.dependsRelationVizDescription.length();

            iterable.forEach((dependency) -> {
                this.dependsRelationVizDescription.append("\"" + dependency.getName() + "\"");
                this.dependsRelationVizDescription.append(", ");
            });

            closeDependencyVizDescription(this.dependsRelationVizDescription, dependencyLengthBefore);
        }

        private void parseModel(IClassModel model) {
            // Get Class information.
            generateHeader(model.getType(), model.getName());
            generateFields(model.getFields());
            generateMethods(model.getMethods());

            // Setup the VizDescriptions.
            generateClassVizDescription();
            generateSuperClassVizDescription(model.getSuperClass().getName());
            generateInterfaceVizDescription(model.getInterfaces());
            generateHasRelationVizDescription(model.getHasRelation());
            generateDependsRelationVizDescription(model.getDependsRelation());
        }
    }
}
