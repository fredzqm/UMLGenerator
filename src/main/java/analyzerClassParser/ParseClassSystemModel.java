package analyzerClassParser;

import analyzer.IClassModel;
import analyzer.IClassModelFilter;
import analyzer.ISystemModel;
import analyzer.ISystemModelFilter;

import java.util.ArrayList;
import java.util.Collection;

public class ParseClassSystemModel extends ISystemModelFilter {
    private IParser<IClassModel> classParser;
    private IClassParserConfiguration config;
    
    /**
     * Construct a ParseClassSystemModel.
     *
     * @param systemModel
     * @param classParser
     */
    public ParseClassSystemModel(ISystemModel systemModel, IParser<IClassModel> classParser,
            IClassParserConfiguration config) {
        super(systemModel);
        this.classParser = classParser;
        this.config = config;
    }
    
    @Override
    public Collection<? extends IClassModel> getClasses() {
        Collection<IClassModel> classes = new ArrayList<>();
        
        super.getClasses().forEach((c) -> {
            classes.add(new IClassModelFilter(c) {
                public String getLabel() {
                    return classParser.parse(c, config);
                }
            });
        });
        return classes;
    }
}
