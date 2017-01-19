package analyzer.singleton;

import java.util.ArrayList;
import java.util.List;

import analyzer.utility.IClassModel;
import analyzer.utility.IClassModelFilter;

public class SingletonClassModel extends IClassModelFilter {

    public SingletonClassModel(IClassModel classModel) {
        super(classModel);
    }
    
    
    @Override
    public List<String> getStereoTypes() {
        List<String> ls = new ArrayList<>(super.getStereoTypes());
        ls.add("Singleton");
        return ls;
    }
    
    @Override
    public String getNodeStyle() {
        return super.getNodeStyle() + " color=\"blue\"";
    }
}
