package generator;

import models.ASMParser;
import models.SystemModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lamd on 12/11/2016.
 */
public class GraphVizGeneratorTest {

    public static void main(String[] args) {
        List<String> classList = new ArrayList<>();
        classList.add("java.lang.Integer");
        ISystemModel systemModel = new SystemModel(classList, new ASMParser());

        GraphVizGenerator generator = new GraphVizGenerator();
        generator.generate(systemModel, null, null);
    }

//    private class DummySystemModel implements ISystemModel {
//
//        @Override
//        public List<? extends IClassModel> getClasses() {
//            DummyClassModel animal = new DummyClassModel("Animal");
//            animal.setType(ClassModel.ClassType.INTERFACE);
//
//            List<IFieldModel> fields = new ArrayList<>();
//            animal.setFields(fields);
//
//            List<IMethodModel> methods = new ArrayList<>();
//            methods.add
//            animal.setMethods(methods);
//
//            return null;
//        }
//    }
//
//    private class DummyClassModel implements IClassModel {
//        private String name;
//        private IClassType type;
//        private Iterable<? extends IFieldModel> fields;
//        private Iterable<? extends IMethodModel> methods;
//        private Iterable<? extends IClassModel> interfaces;
//        private Iterable<? extends IClassModel> hasRelation;
//        private Iterable<? extends IClassModel> dependsRelation;
//
//        private IClassModel superClass;
//
//        public DummyClassModel(String name) {
//            this.name = name;
//        }
//
//        public void setName(String name) {
//            this.name = name;
//        }
//
//        public void setType(IClassType type) {
//            this.type = type;
//        }
//
//        public void setFields(Iterable<? extends IFieldModel> fields) {
//            this.fields = fields;
//        }
//
//        public void setMethods(Iterable<? extends IMethodModel> methods) {
//            this.methods = methods;
//        }
//
//        public void setSuperClass(IClassModel superClass) {
//            this.superClass = superClass;
//        }
//
//        public void setInterfaces(Iterable<? extends IClassModel> interfaces) {
//            this.interfaces = interfaces;
//        }
//
//        public void setHasRelation(Iterable<? extends IClassModel> hasRelation) {
//            this.hasRelation = hasRelation;
//        }
//
//        public void setDependsRelation(Iterable<? extends IClassModel> dependsRelation) {
//            this.dependsRelation = dependsRelation;
//        }
//
//        @Override
//        public String getName() {
//            return this.name;
//        }
//
//        @Override
//        public IClassType getType() {
//            return this.type;
//        }
//
//        @Override
//        public Iterable<? extends IFieldModel> getFields() {
//            return this.fields;
//        }
//
//        @Override
//        public Iterable<? extends IMethodModel> getMethods() {
//            return this.methods;
//        }
//
//        @Override
//        public IClassModel getSuperClass() {
//            return this.superClass;
//        }
//
//        @Override
//        public Iterable<? extends IClassModel> getInterfaces() {
//            return this.interfaces;
//        }
//
//        @Override
//        public Iterable<? extends IClassModel> getHasRelation() {
//            return this.hasRelation;
//        }
//
//        @Override
//        public Iterable<? extends IClassModel> getDependsRelation() {
//            return this.dependsRelation;
//        }
//    }
//
//    private class DummyMethodModel implements IMethodModel {
//        private IClassModel parent;
//        private String name;
//        private IModifier modifier;
//        private Collection<? extends ITypeModel> arguments;
//        private ITypeModel returnType;
//        private Collection<? extends IMethodModel> dependentMethods;
//        private Collection<? extends IFieldModel> dependentFields;
//
//        public DummyMethodModel(String returnType, String name, ) {
//            this.name = name;
//        }
//
//        @Override
//        public IClassModel getParentClass() {
//            return this.parent;
//        }
//
//        @Override
//        public String getName() {
//            return this.name;
//        }
//
//        @Override
//        public IModifier getModifier() {
//            return this.modifier;
//        }
//
//        @Override
//        public Collection<? extends ITypeModel> getArguments() {
//            return this.arguments;
//        }
//
//        @Override
//        public ITypeModel getReturnType() {
//            return this.returnType;
//        }
//
//        @Override
//        public Collection<? extends IMethodModel> getDependentMethods() {
//            return this.dependentMethods;
//        }
//
//        @Override
//        public Collection<? extends IFieldModel> getDependentFields() {
//            return this.dependentFields;
//        }
//
//        @Override
//        public boolean isFinal() {
//            return false;
//        }
//
//        public void setParent(IClassModel parent) {
//            this.parent = parent;
//        }
//
//        public void setName(String name) {
//            this.name = name;
//        }
//
//        public void setModifier(IModifier modifier) {
//            this.modifier = modifier;
//        }
//
//        public void setArguments(Collection<? extends ITypeModel> arguments) {
//            this.arguments = arguments;
//        }
//
//        public void setReturnType(ITypeModel returnType) {
//            this.returnType = returnType;
//        }
//
//        public void setDependentMethods(Collection<? extends IMethodModel> dependentMethods) {
//            this.dependentMethods = dependentMethods;
//        }
//
//        public void setDependentFields(Collection<? extends IFieldModel> dependentFields) {
//            this.dependentFields = dependentFields;
//        }
//    }
}