package generator;

import java.util.Collection;

/**
 * Created by lamd on 12/17/2016.
 */
public interface IParseGuideFactory {
    IParseGuide<IClassModel> createClassParser();
    IParseGuide<IClassModel> createSuperClassParser();
    IParseGuide<IClassModel> createInterfaceParser();
    IParseGuide<IClassModel> createHasParser();
    IParseGuide<IClassModel> createDependsOnParser();
    Collection<IParseGuide<IClassModel>> createCustomParser();
}
