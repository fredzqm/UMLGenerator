package adapter.classParser;

import analyzer.utility.IClassModel;
import analyzer.utility.IFieldModel;
import analyzer.utility.IMethodModel;
import analyzer.utility.ITypeModel;
import config.Configurable;
import config.IConfiguration;
import utility.IFilter;
import utility.Modifier;

/**
 * Concrete Configuration object of IClassParserConfiguration.
 * <p>
 * Created by lamd on 1/11/2017.
 */
public class ClassParserConfiguration implements Configurable {
    public static final String CONFIG_PATH = "classParser.";

    public static final String HEADER_PARSER = CONFIG_PATH + "header";
    public static final String FIELD_PARSER = CONFIG_PATH + "field";
    public static final String METHOD_PARSER = CONFIG_PATH + "method";
    public static final String TYPE_PARSER = CONFIG_PATH + "type";
    public static final String MODIFIER_PARSER = CONFIG_PATH + "modifier";
    public static final String MODIFIER_FILTER = CONFIG_PATH + "modifierFilter";
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
        this.config.setIfMissing(FIELD_PARSER, GraphVizFieldParser.class.getName());
        this.config.setIfMissing(METHOD_PARSER, GraphVizMethodParser.class.getName());
        this.config.setIfMissing(TYPE_PARSER, GraphVizTypeParser.class.getName());
        this.config.setIfMissing(MODIFIER_PARSER, GraphVizModifierParser.class.getName());
    }

    /**
     * Return the set of Method Access Filters.
     *
     * @return Set of Method Access Filters.
     */
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

    /**
     * @return class as parser for the header
     */
    public IParser<IClassModel> getHeaderParser() {
        return (IParser<IClassModel>) getParser(config.getValue(HEADER_PARSER));
    }

    /**
     * @return class as the field parser
     */
    public IParser<IFieldModel> getFieldParser() {
        return (IParser<IFieldModel>) getParser(config.getValue(FIELD_PARSER));
    }

    /**
     * @return class as the method parser
     */
    public IParser<IMethodModel> getMethodParser() {
        return (IParser<IMethodModel>) getParser(config.getValue(METHOD_PARSER));
    }

    /**
     * @return class as the type parser
     */
    public IParser<ITypeModel> getTypeParser() {
        return (IParser<ITypeModel>) getParser(config.getValue(TYPE_PARSER));
    }

    /**
     * @return
     */
    public IParser<Modifier> getModifierParser() {
        return (IParser<Modifier>) getParser(config.getValue(MODIFIER_PARSER));
    }

    private IParser<?> getParser(String className) {
        IParser<?> classParser = IConfiguration.instantiateWithName(className, IParser.class);
        return classParser;
    }
}
