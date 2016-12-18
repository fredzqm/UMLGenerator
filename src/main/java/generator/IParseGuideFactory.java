package generator;

import java.util.Collection;

/**
 * Created by lamd on 12/17/2016.
 */
public interface IParseGuideFactory {
    IParseGuide<IClassModel> createClassParser();
    Collection<IParseGuide<IClassModel>> createRelationshipParsers();
}
