package analyzerClassParser;

import utility.IFilter;
import utility.Modifier;

public interface IClassParserConfiguration {
	/**
	 * Return the set of Method Access Filters.
	 *
	 * @return Set of Method Access Filters.
	 */
	IFilter<Modifier> getModifierFilters();
}
