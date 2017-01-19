package analyzer.singleton;

import java.util.ArrayList;
import java.util.Collection;

import analyzer.utility.IClassModel;
import analyzer.utility.IClassModelFilter;

public class SingletonClassModel extends IClassModelFilter {

    public SingletonClassModel(IClassModel classModel) {
        super(classModel);
    }
    
    
    @Override
    public Collection<String> getStereoTypes() {
        Collection<String> ls = new ArrayList<>(super.getStereoTypes());
        ls.add("Singleton");
        return ls;
    }
}
