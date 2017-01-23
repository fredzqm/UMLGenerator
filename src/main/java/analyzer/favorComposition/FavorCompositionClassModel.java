package analyzer.favorComposition;

import analyzer.utility.IClassModel;
import analyzer.utility.IClassModelFilter;

/**
 * Created by lamd on 1/15/2017.
 */
public class FavorCompositionClassModel extends IClassModelFilter {

    public FavorCompositionClassModel(IClassModel classModel) {
        super(classModel);
    }

    @Override
    public String getNodeStyle() {
        return super.getNodeStyle() + " color=\"orange\"";
    }

}
