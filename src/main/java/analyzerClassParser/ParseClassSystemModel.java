package analyzerClassParser;

import java.util.ArrayList;
import java.util.Collection;

import analyzer.IClassModel;
import analyzer.ISystemModel;
import analyzer.ISystemModelFilter;

public class ParseClassSystemModel extends ISystemModelFilter {
    private IClassParserConfiguration config;

    /**
     * Construct a ParseClassSystemModel.
     *
     * @param systemModel
     * @param classParser
     */
    public ParseClassSystemModel(ISystemModel systemModel, IClassParserConfiguration config) {
        super(systemModel);
        this.config = config;
    }

    @Override
    public Collection<? extends IClassModel> getClasses() {
        Collection<IClassModel> classes = new ArrayList<>();

        super.getClasses().forEach((c) -> {
            classes.add(new GraphVizClass(c, config));
        });
        return classes;
    }
}
