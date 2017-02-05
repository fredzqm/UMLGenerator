package adapter.classParser;

import java.util.HashSet;
import java.util.Set;

import analyzer.utility.IClassModel;
import analyzer.utility.ISystemModel;
import analyzer.utility.ISystemModelFilter;

public class ParseClassSystemModel extends ISystemModelFilter {
    private ClassParserConfiguration config;

    /**
     * Construct a ParseClassSystemModel.
     *
     * @param systemModel
     */
    ParseClassSystemModel(ISystemModel systemModel, ClassParserConfiguration config) {
        super(systemModel);
        this.config = config;
    }

    @Override
    public Set<? extends IClassModel> getClasses() {
        Set<IClassModel> classes = new HashSet<>();

        super.getClasses().forEach((c) -> {
            classes.add(new GraphVizClass(c, config));
        });
        return classes;
    }
}
