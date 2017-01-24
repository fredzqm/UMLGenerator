package analyzer.singleton;

import java.util.ArrayList;
import java.util.List;

import analyzer.utility.IClassModel;
import analyzer.utility.IClassModelFilter;

/**
 * TODO: Fred
 */
public class SingletonClassModel extends IClassModelFilter {
    private SingletonConfiguration config;

    SingletonClassModel(IClassModel classModel, SingletonConfiguration config) {
        super(classModel);
        this.config = config;
    }

    @Override
    public List<String> getStereoTypes() {
        List<String> ls = new ArrayList<>(super.getStereoTypes());
        ls.add("Singleton");
        return ls;
    }

    @Override
    public String getNodeStyle() {
        return String.format("%s color=\"%s\"", super.getNodeStyle(), this.config.getSingletonColor());
    }
}
