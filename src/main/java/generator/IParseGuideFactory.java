package generator;

import java.util.Collection;

/**
 * An interface for the ParseGuide factory.
 * <p>
 * Created by lamd on 12/17/2016.
 */
public interface IParseGuideFactory {
    /**
     * Returns the class parser.
     *
     * @return ParseGuide of the Class.
     */
    IParseGuide createClassParser();

    /**
     * Create super class, interfaces, has-a, and/or depends-on parsers.
     *
     * @return Collection of relationship ParseGuides
     */
    Collection<IParseGuide> createRelationshipParsers();
}
