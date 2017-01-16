package config;

import analyzer.classParser.*;
import analyzer.utility.IClassModel;
import analyzer.utility.IFieldModel;
import analyzer.utility.IMethodModel;
import analyzer.utility.ITypeModel;
import utility.IFilter;
import utility.Modifier;

/**
 * Created by lamd on 1/11/2017.
 */
public class ClassParserConfiguration implements IClassParserConfiguration, Configurable {
    public static final String HEADER = "class_header";
    public static final String FIELD = "class_field";
    public static final String METHOD = "class_method";
    public static final String TYPE = "class_type";
    public static final String MODIFIER = "class_modifier";

    private IConfiguration config;

    /**
     * Empty constructor for newInstance calls.
     */
    public ClassParserConfiguration() {
        this.config = null;
    }

    @Override
    public void setup(IConfiguration config) {
        this.config = config;

        IFilter<Modifier> filter = data -> false;
        this.config.setFilterIfMissing(filter);
        this.config.setIfMissing(ClassParserConfiguration.HEADER, GraphVizHeaderParser.class);
        this.config.setIfMissing(ClassParserConfiguration.FIELD, GraphVizFieldParser.class);
        this.config.setIfMissing(ClassParserConfiguration.METHOD, GraphVizMethodParser.class);
        this.config.setIfMissing(ClassParserConfiguration.TYPE, GraphVizTypeParser.class);
        this.config.setIfMissing(ClassParserConfiguration.MODIFIER, GraphVizModifierParser.class);
    }

    @Override
    public IFilter<Modifier> getModifierFilters() {
        return this.config.getModifierFilter();
    }

    @Override
    public IParser<IClassModel> getHeaderParser() {
        try {
            return (IParser<IClassModel>) this.config.getClass(ClassParserConfiguration.HEADER).newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            throw new RuntimeException("Header Parser missing empty contructor", e);
        }
    }

    @Override
    public IParser<IFieldModel> getFieldParser() {
        try {
            return (IParser<IFieldModel>) this.config.getClass(ClassParserConfiguration.FIELD).newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            throw new RuntimeException("Field Parser missing empty contructor", e);
        }
    }

    @Override
    public IParser<IMethodModel> getMethodParser() {
        try {
            return (IParser<IMethodModel>) this.config.getClass(ClassParserConfiguration.METHOD).newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            throw new RuntimeException("Method Parser missing empty contructor", e);
        }
    }

    @Override
    public IParser<ITypeModel> getTypeParser() {
        try {
            return (IParser<ITypeModel>) this.config.getClass(ClassParserConfiguration.TYPE).newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            throw new RuntimeException("Type Parser missing empty contructor", e);
        }
    }

    @Override
    public IParser<Modifier> getModifierParser() {
        try {
            return (IParser<Modifier>) this.config.getClass(ClassParserConfiguration.MODIFIER).newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            throw new RuntimeException("Modifier Parser missing empty contructor", e);
        }
    }
}
