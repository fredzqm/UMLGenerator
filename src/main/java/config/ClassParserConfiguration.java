package config;

import analyzer.classParser.*;
import analyzer.utility.IClassModel;
import analyzer.utility.IFieldModel;
import analyzer.utility.IMethodModel;
import analyzer.utility.ITypeModel;
import utility.IFilter;
import utility.Modifier;

/**
 * Concrete Configuration object of IClassParserConfiguration.
 * <p>
 * Created by lamd on 1/11/2017.
 */
public class ClassParserConfiguration implements IClassParserConfiguration {
    public static final String HEADER = "class_header";
    public static final String FIELD = "class_field";
    public static final String METHOD = "class_method";
    public static final String TYPE = "class_type";
    public static final String MODIFIER = "class_modifier";
    public static final String MODIFIER_FILTER = "MODIFIER_FILTER";
    public static final String MODIFIER_FILTER_PUBLIC = "public";
    public static final String MODIFIER_FILTER_PROTECTED = "protected";
    public static final String MODIFIER_FILTER_PRIVATE = "private";

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
        this.config.setIfMissing(MODIFIER_FILTER, MODIFIER_FILTER_PRIVATE);
        this.config.setIfMissing(HEADER, GraphVizHeaderParser.class.getName());
        this.config.setIfMissing(FIELD, GraphVizFieldParser.class.getName());
        this.config.setIfMissing(METHOD, GraphVizMethodParser.class.getName());
        this.config.setIfMissing(TYPE, GraphVizTypeParser.class.getName());
        this.config.setIfMissing(MODIFIER, GraphVizModifierParser.class.getName());
    }

    @Override
    public IFilter<Modifier> getModifierFilters() {
        switch (config.getValue(MODIFIER_FILTER)) {
            case MODIFIER_FILTER_PUBLIC:
                return (m) -> m == Modifier.PUBLIC;
            case MODIFIER_FILTER_PROTECTED:
                return (m) -> m == Modifier.PUBLIC || m == Modifier.PROTECTED;
            default:
                return (m) -> true;
        }
    }

    @Override
    public IParser<IClassModel> getHeaderParser() {
        return (IParser<IClassModel>) getParser(config.getValue(HEADER));
    }

    @Override
    public IParser<IFieldModel> getFieldParser() {
        return (IParser<IFieldModel>) getParser(config.getValue(FIELD));
    }

    @Override
    public IParser<IMethodModel> getMethodParser() {
        return (IParser<IMethodModel>) getParser(config.getValue(METHOD));
    }

    @Override
    public IParser<ITypeModel> getTypeParser() {
        return (IParser<ITypeModel>) getParser(config.getValue(TYPE));
    }

    @Override
    public IParser<Modifier> getModifierParser() {
        return (IParser<Modifier>) getParser(config.getValue(MODIFIER));
    }

    private IParser getParser(String className) {
        IParser classParser = IConfiguration.instantiateWithName(className, IParser.class);
        return classParser;
    }
}
