package analyzer.favorComposition;

import analyzer.utility.IClassModel;
import analyzer.utility.IClassModelFilter;

/**
 * Created by lamd on 1/15/2017.
 */
public class FavorCompositionClassModel extends IClassModelFilter {
    private FavorCompositionConfiguration config;

    FavorCompositionClassModel(IClassModel classModel, FavorCompositionConfiguration config) {
        super(classModel);
        this.config = config;
    }

    @Override
    public String getNodeStyle() {
        return String.format("%s color=\"%s\"", super.getNodeStyle(), this.config.getFavorComColor());
    }

}
