package analyzer.classParser;

import analyzer.classParser.*;
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
	public static final String HEADER = "class_header";
	public static final String FIELD = "class_field";
	public static final String METHOD = "class_method";
	public static final String TYPE = "class_type";
	public static final String MODIFIER = "class_modifier";
	public static final String MODIFIER_FILTER = "modifierFilter";
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
		return (IParser<IClassModel>) getParser(config.getValue(HEADER));
	}

	/**
	 * @return class as the field parser
	 */
	public IParser<IFieldModel> getFieldParser() {
		return (IParser<IFieldModel>) getParser(config.getValue(FIELD));
	}

	/**
	 * @return class as the method parser
	 */
	public IParser<IMethodModel> getMethodParser() {
		return (IParser<IMethodModel>) getParser(config.getValue(METHOD));
	}

	/**
	 * @return class as the type parser
	 */
	public IParser<ITypeModel> getTypeParser() {
		return (IParser<ITypeModel>) getParser(config.getValue(TYPE));
	}

	/**
	 * @return
	 */
	public IParser<Modifier> getModifierParser() {
		return (IParser<Modifier>) getParser(config.getValue(MODIFIER));
	}

	private IParser<?> getParser(String className) {
		IParser<?> classParser = IConfiguration.instantiateWithName(className, IParser.class);
		return classParser;
	}
}
